#include <queue>
#include <mutex>
#include <condition_variable>
#include <atomic>
#include <thread>
#include <sstream>

template <class T>
class Queue
{
public:
	Queue() : m_fShuttingDown(false) { }

	size_t Size()
	{ 
		return m_q.size();  
	}

	void Shutdown()
	{
		unique_lock<mutex> lock(m_mutex);

		m_fShuttingDown = true;

		cout << "Shutting down queue with " << m_waitQ.size() << "waiters and " << m_q.size() << "queued items..." << endl;

		if (!m_q.empty())
		{
			cout << "Waiting until queue drains ..." << endl;
			m_emptyQ.wait(lock);
			assert(m_q.empty());
		}

		if (!m_waitQ.empty())
		{
			cout << "Waiting until all consumers exit ..." << endl;
			m_waitQ.front()->notify_one();
			m_emptyWaitQ.wait(lock);
			assert(m_waitQ.empty());
		}

		cout << "Queue shutdown is complete with " << m_waitQ.size() << "waiters and " << m_q.size() << "queued items..." << endl;
	}

	void Enqueue(const T& t)
	{
		unique_lock<mutex> guard(m_mutex);

		if (m_fShuttingDown)
		{
			throw exception("Attempted to enqueue on a queue that has already been shutdown.");
		}
		// Enqueue the data.
		//
		m_q.push(t);

		// Notify any consumer threads on the wait queue.
		//
		if (!m_waitQ.empty())
		{
			cout << "Producer notifying a waiter after an enqueue ..." << endl;
			m_waitQ.front()->notify_one();
		}
	}

	T Dequeue()
	{
		T t;
		{
			unique_lock<mutex> lock(m_mutex);
			assert(lock.owns_lock());

			if (m_fShuttingDown && m_q.empty())
			{
				throw exception("Attempted to dequeue on a queue that is already shutdown and empty!");
			}

			// If the Q is empty or there are any threads already waiting, get in the wait queue.
			//
			if (m_q.empty() || !m_waitQ.empty())
			{
				cout << "Consumer waiting for non-empty queue with " << m_waitQ.size() << " waiters..." << endl;
				CondVarPtr pCondVarEntering(new condition_variable());
				m_waitQ.push(pCondVarEntering);
				m_waitQ.back()->wait(lock);

				// The condition variable the thread waited on should be the one that is popped from the queue.
				//
				CondVarPtr pCondVarExiting = m_waitQ.front();
				m_waitQ.pop();
				assert(pCondVarEntering == pCondVarExiting);

				if (m_waitQ.empty())
				{
					cout << "Consumer notifying shutdown thread that wait queue is empty ..." << endl;
					m_emptyWaitQ.notify_one();
				}
				else
				{
					if (m_fShuttingDown)
					{
						m_waitQ.front()->notify_one();
					}
				}
				
				if (m_q.empty())
				{
					assert(m_fShuttingDown);
					throw exception("Consumer awoken due to queue shutdown event with empty queue!");
				}

				// After the thread is awoken, the queue must be non-empty.
				//
				assert(m_q.size() > 0);
			}
			
			t = m_q.front();
			m_q.pop();

			cout << "Queue size: " << m_q.size() << endl;

			if (!m_q.empty() && !m_waitQ.empty())
			{
				cout << "Consumer notifying a waiter after an dequeue ..." << endl;
				m_waitQ.front()->notify_one();
			}

			if (m_q.empty())
			{
				cout << "Consumer notifying shutdown thread that queue is empty ..." << endl;
				m_emptyQ.notify_all();
			}
		}

		return t;
	}

private:
	typedef shared_ptr<condition_variable> CondVarPtr;
	typedef queue<CondVarPtr> WaitQueue;
	

	WaitQueue m_waitQ;
	condition_variable m_emptyQ;
	condition_variable m_emptyWaitQ;

	mutex m_mutex;
	queue<T> m_q;
	bool m_fShuttingDown;
};

static Queue<string> q;
static atomic<int> s_consumerThreadId = 0;
static atomic<int> s_producerThreadId = 0;

template <class T>
void ConsumerThread()
{
	int threadId = ++s_consumerThreadId;
	try
	{
		while (true)
		{
			T t = q.Dequeue();
			cout << "Consumed (" << threadId << "): " << t << endl;
			if (rand() % 5 == 0)
			{
				this_thread::sleep_for(chrono::milliseconds(1));
			}
		}
	}
	catch (exception& e)
	{
		cout << e.what() << endl;
	}

	cout << "Consumer exited (" << threadId << ")" << endl;
}

void ProducerThread()
{
	int threadId = ++s_producerThreadId;

	for (int i = 0; i < 10; ++i)
	{
		ostringstream os;
		os << "Data item " << i;
		q.Enqueue(os.str());
		if (rand() % 5 == 0)
		{
			this_thread::sleep_for(chrono::milliseconds(1));
		}
	}

	cout << "Producer exited (" << threadId << ")" << endl;
}

void TestProducerConsumerQueue()
{
	vector<thread*> producers;
	vector<thread*> consumers;

	for (int i = 0; i < 5; ++i)
	{
		consumers.push_back(new thread(ConsumerThread<string>));
	}

	for (int i = 0; i < 3; ++i)
	{
		producers.push_back(new thread(ProducerThread));
	}

	for (vector<thread*>::iterator i = producers.begin(); i < producers.end(); ++i)
	{
		(*i)->join();
	}

	cout << "Producers have completed. Waiting for the queue to drain ..." << endl;
	q.Shutdown();
	
	for (vector<thread*>::iterator i = consumers.begin(); i < consumers.end(); ++i)
	{
		(*i)->join();
	}
	cout << "Consumers have exited ..." << endl;
}
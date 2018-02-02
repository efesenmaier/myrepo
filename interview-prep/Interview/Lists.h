template <class T>
struct ListItem
{
	ListItem(const T& val) : m_next(nullptr), m_val(val) { }

	ListItem<T>* m_next;
	T m_val;
};

template <class T>
class List
{
public:
	List() : m_head(nullptr) { }

	void InsertFront(const T& val)
	{
		ListItem<T>* item = new ListItem<T>(val);
		item->m_next = m_head;
		m_head = item;
	}

	void InsertBack(const T& val)
	{
		ListItem<T>* item = new ListItem<T>(val);

		if (m_head)
		{
			// If the list is non-empty, find the last element by iterating until the next
			// pointer is null.
			//
			ListItem<T>* cur = nullptr;
			ListItem<T>* next = nullptr;
			while (nullptr != (next = GetNext(cur)))
			{
				cur = next;
			}
			// Since the list is non-empty, this code should always find the last element.
			//
			assert(nullptr != cur);
			cur->m_next = item;
		}
		else
		{
			// If the list is empty, this is the trivial case. Just update the head pointer.
			//
			m_head = item;
		}
	}

	ListItem<T>* GetNext(const ListItem<T>* item) const
	{
		if (nullptr == item)
		{
			return m_head;
		}
		return item->m_next;
	}
	
	void RemoveFront()
	{
		std::unique_ptr<ListItem<T>> deleteMe(m_head);
		if (m_head)
		{
			m_head = m_head->m_next;
		}
	}

	bool IsEmpty() { return nullptr == m_head; }
		
	void Clear()
	{
		while (!IsEmpty()) RemoveFront();
	}

	const ListItem<T>* FindSecondToLast()
	{
		return FindNthToLast(2);
	}


	// FindNthToLast(1) => Return last element of list. 
	//
	const ListItem<T>* FindNthToLast(int n)
	{
		if (n < 1) throw std::exception("Invalid argument for Nth to last!");

		ListItem<T>* prev = m_head;
		ListItem<T>* cur = m_head;

		while (cur)
		{
			if (n >= 1) --n;
			else
			{
				prev = prev->m_next;
			}
			cur = cur->m_next;
		}

		// TODO: Track size and perform this as a pre-check.
		//
		if (nullptr == prev) throw std::exception("List is too short to contain Nth to last item!");

		return prev;
	}

	ListItem<T>* FindFirstItemWithValue(const T& val)
	{
		ListItem<T>* cur = m_head;

		while (cur && val != cur->m_val)
		{
			cur = cur->m_next;
		}

		if (cur) return cur;

		throw std::exception("List item not found!");
	}

	void Delete(ListItem<T>* deleteMe)
	{
		if (nullptr == deleteMe)
		{
			throw std::exception("Invalid argument!");
		}

		if (deleteMe == m_head)
		{
			RemoveFront();
			return;
		}

		ListItem<T>* cur = m_head;
		while (cur && cur->m_next != deleteMe)
		{
			cur = cur->m_next;
		}

		if (nullptr == cur) throw std::exception("List item not found!");

		assert(cur);
		assert(cur->m_next);
		assert(deleteMe == cur->m_next);

		std::unique_ptr<ListItem<T>> autoDelete(cur->m_next);
		cur->m_next = cur->m_next->m_next;
	}

private:
	ListItem<T>* m_head;
};

template <class T>
std::ostream& operator<<(std::ostream& os, const List<T>& list)
{
	ListItem<T>* item = nullptr;
	while (item = list.GetNext(item))
	{
		os << item->m_val << " ";
	}
	return os;
}
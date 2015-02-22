#include <hash_map>
#include <list>
#include <memory>

template <class T>
struct CacheEntry
{
	CacheEntry(int key, const T& value) : m_key(key), m_value(value) {}

	int m_key;
	T m_value; 
	typename std::list<std::shared_ptr<CacheEntry<T>>>::iterator m_iter;
};

template <class T>
class LRUCache
{
public:
	LRUCache() : m_maxSize(1024) { }
	LRUCache(size_t maxSize) : m_maxSize(maxSize) { }

	typedef std::shared_ptr<CacheEntry<T>> cache_entry_ptr;
	typedef std::hash_map<int, cache_entry_ptr> cache_map;
	typedef std::list<cache_entry_ptr> cache_list;

	friend std::ostream& operator<< <>(std::ostream& os, const LRUCache<T>& cache);

private:
	cache_map m_cache;
	cache_list m_list;

	const size_t m_maxSize;

	void Add(int key, const T& value)
	{
		cache_entry_ptr newEntry(new CacheEntry<T>(key, value));
		m_cache[key] = newEntry;
		m_list.push_front(newEntry);
		newEntry->m_iter = m_list.begin();
	}

public:
	void AddOrUpdate(int key, const T& value)
	{
		// If the key is already in the cache, then update the cache item and refresh the age of the entry.
		//
		cache_map::iterator existingItemIter = m_cache.find(key);
		cache_entry_ptr existingItem;
		if (existingItemIter != m_cache.end())
		{
			existingItem = existingItemIter->second;
		}

		if (existingItem.get())
		{
			existingItem->m_value = value;

			m_list.erase(existingItem->m_iter);
			m_list.push_front(existingItem);
			existingItem->m_iter = m_list.begin();

			return;
		}

		// If cache is not full, add the cache entry. Otherwise, expire (remove) the oldest entry and add the new
		// cache entry.
		//
		if (m_list.size() < m_maxSize)
		{
			Add(key, value);
		}
		else
		{
			cache_entry_ptr oldestEntry(m_list.back());
			m_list.pop_back();
			m_cache.erase(oldestEntry->m_key);

			Add(key, value);
		}
	}

	bool Lookup(int key, T& value /* [out] */)
	{
		// If the lookup is a cache miss, return an error.
		//
		cache_map::iterator iter = m_cache.find(key);

		if (m_cache.end() == iter)
		{
			return false;
		}

		// Refresh the age of the item when accessed.
		//
		m_list.erase(iter->second->m_iter);
		m_list.push_front(iter->second);
		iter->second->m_iter = m_list.begin();
		value = iter->second->m_value;

		return true;
	}
};

template <class T>
std::ostream& operator<<(std::ostream& os, const LRUCache<T>& cache)
{
	for (LRUCache<T>::cache_list::const_iterator i = cache.m_list.cbegin(); i != cache.m_list.end(); ++i)
	{
		os << "(" << i->get()->m_key << "," << i->get()->m_value << "), ";
	}
	return os;
}
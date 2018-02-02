using namespace std;

/* qsort int comparison function */
int int_cmp(const void *a, const void *b)
{
	const int *ia = (const int *)a; // casting pointer types 
	const int *ib = (const int *)b;
	return *ia - *ib;
	/* integer comparison: returns negative if b > a
	and positive if a > b */
}

void PrintAllPairsWithSum(int* iarr, size_t length, int targetSum)
{
	size_t left = 0;
	size_t right = length - 1;

	while (left < right)
	{
		int sum = iarr[left] + iarr[right];

		if (sum < targetSum) ++left;
		else if (sum > targetSum) --right;
		else
		{
			assert(sum == targetSum);

			for (size_t tempLeft = left; tempLeft < right && iarr[tempLeft] == iarr[left]; ++tempLeft)
			{
				std::cout << iarr[tempLeft] << ", " << iarr[right] << std::endl;
			}
			--right;
		}
	}
}

void SortInPlace(std::vector<int>& vec)
{
	std::sort(vec.begin(), vec.end());
}

/* O(N*lg(N)) implementation. In place sort and compare (no extra memory). */
std::vector<int> FindIntersectionsBySortedCompare(
	const std::vector<int>& array1,
	const std::vector<int>& array2
	)
{
	std::vector<int> arr1 = array1;
	std::vector<int> arr2 = array2;
	std::vector<int> intersections;

	SortInPlace(arr1);
	SortInPlace(arr2);

	size_t i = 0;
	size_t j = 0;

	while (i < arr1.size() && j < arr2.size())
	{
		// If values match, increment one counter (doesn't matter which one).
		// Else if first value is less than second increment first counter.
		// Else increment second counter.
		//
		if (arr1[i] < arr2[j])
		{
			++i;
		}
		else if (arr1[i] > arr2[j])
		{
			++j;
		}
		else
		{
			if (intersections.size() ==0 || intersections.back() != arr1[i])
			{
				intersections.push_back(arr1[i]);
			}
			++i;
			++j;
		}
	}

	return intersections;
}

/* O(N*lg(N)) implementation. In place sort and compare (no extra memory). */
std::vector<int> FindIntersectionsByHashSetLookup(
	const std::vector<int>& arr1,
	const std::vector<int>& arr2)
{
	std::hash_set<int> set1;
	std::vector<int> intersections;
	for (vector<int>::const_iterator i = arr1.begin(); i < arr1.end(); ++i)
	{
		set1.insert(*i);
	}

	for (vector<int>::const_iterator i = arr2.begin(); i < arr2.end(); ++i)
	{
		if (set1.find(*i) != set1.end())
		{
			intersections.push_back(*i);
			set1.erase(*i);
		}
	}

	return intersections;
}

// Tennis tournament: List of Matches. Each match has a winner and loser. Does there exist 3 players A, B, C, such that A defeated B, B defeated C, and C defeated A.
struct Match {
	std::string winner; // name of winner
	std::string loser;
};

typedef std::vector<std::string> Cycle;

bool Does3CycleExist(const std::vector<Match>& matches)
{
	// 1. Setup a set of the players.
	// 2. Setup the "defeats" relation set for each player.
	// 3. Select a unique triple of players and check if the condition exists.
	//
	std::hash_set<std::string> players;
	std::hash_map<std::string, std::hash_set<std::string>> defeats;

	for (size_t i = 0; i < matches.size(); ++i)
	{
		players.insert(matches[i].winner);
		players.insert(matches[i].loser);

		// Assume the hash is default constructed. TODO: test.
		//
		defeats[matches[i].winner].insert(matches[i].loser);
	}


	/*set<Cycle> cycles = GenerateCycles(players);


	for (set<Cycle>::iterator i = cycles.begin(); i < cycles.end(); ++i)
	{
		if (DoesCycleExist(cycle, defeats)) return true;
	}*/

	return false;
}


bool DoesCycleExist(
	const Cycle& cycle,
	std::hash_map<std::string, std::hash_set<std::string>>& defeats
	)
{
	for (vector<string>::const_iterator i = cycle.cbegin(); i < cycle.end(); ++i)
	{
		vector<string>::const_iterator j = i + 1;
		if (j == cycle.end()) j = cycle.cbegin();

		if (defeats[*i].find(*j) == defeats[*i].end())
			return false;
	}

	return true;
}
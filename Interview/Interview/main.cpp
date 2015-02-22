#include <iostream>
#include <Lists.h>
#include <Strings.h>
#include <Trees.h>
#include <Arrays.h>
#include <LRUCache.h>
#include <cassert>

#include <iterator>

int main(int argc, char** argv)
{
	std::cout << "Hello World!!!" << std::endl;

	List<int> list;
	list.InsertFront(2);
	list.InsertFront(1);
	list.InsertFront(0);

	list.InsertBack(3);
	list.InsertBack(4);
	list.InsertBack(5);

	std::cout << list << std::endl;

	{
		const ListItem<int>* listItem = list.FindNthToLast(1);
		std::cout << "First to Last: " << (listItem == nullptr ? 999 : listItem->m_val) << std::endl;
		assert(5 == listItem->m_val);
	}

	try
	{
		std::cout << "Zeroth to Last: ";
		const ListItem<int>* listItem = list.FindNthToLast(0);
		std::cout << (listItem == nullptr ? 999 : listItem->m_val) << std::endl;
	}
	catch (const std::exception& e)
	{
		std::cout << "Exception:" << e.what() << std::endl;
	}

	{
		const ListItem<int>* listItem = list.FindSecondToLast();
		std::cout << "Second to Last: " << (listItem == nullptr ? 999 : listItem->m_val) << std::endl;
		assert(4 == listItem->m_val);
	}

	{
		const ListItem<int>* listItem = list.FindNthToLast(3);
		std::cout << "Third to Last: " << (listItem == nullptr ? 999 : listItem->m_val) << std::endl;
		assert(3 == listItem->m_val);
	}

	try
	{
		ListItem<int>* listItem = list.FindFirstItemWithValue(3);
		std::cout << "List item with value 3:" << listItem << std::endl;

		std::cout << "Deleting first list item with value 3." << std::endl;
		list.Delete(listItem);
		std::cout << list << std::endl;

		std::cout << "Deleting same list item with value 3 again." << std::endl;
		list.Delete(listItem);
	}
	catch (const std::exception& e)
	{
		std::cout << "Exception:" << e.what() << std::endl;
	}

	try
	{
		std::cout << "Finding list item with value 6." << std::endl;
		const ListItem<int>* listItem = list.FindFirstItemWithValue(6);
		std::cout << listItem << std::endl;
	}
	catch (const std::exception& e)
	{
		std::cout << "Exception:" << e.what() << std::endl;
	}


	assert(5 == RomanToInt("V"));
	assert(5 == RomanToInt("VX"));
	assert(85 == RomanToInt("VXC"));
	assert(6 == RomanToInt("VI"));
	assert(10 == RomanToInt("VV"));
	assert(89 == RomanToInt("IVVC"));

	assert (2000 == RomanToInt("MM"));
	assert (1999 == RomanToInt("MCMXCIX"));
	assert(2013 == RomanToInt("MMXIII"));
	assert(2014 == RomanToInt("MMXIV"));
	assert(2016 == RomanToInt("MMXVI"));
	assert(18 == RomanToInt("XIIX"));
	assert(18 == RomanToInt("IIXX"));
	assert(4 == RomanToInt("IVX"));

	std::hash_map<std::string, int> dictionary;
	std::vector<std::hash_map<std::string, int>::iterator> repeatingWords;
	FindRepeatingWords("Ba ba black sheep, have you any wool. Yes sir, yes sir, three bags full. ba ba ba.", dictionary, repeatingWords);
	for (std::vector<std::hash_map<std::string, int>::iterator>::iterator i = repeatingWords.begin(); i != repeatingWords.end(); ++i)
	{
		std::cout << (*i)->first << " ";
	}
	std::cout << std::endl;

	{
		std::string reverseMe = "ABCDEF";
		ReverseStringInPlace(reverseMe);
		std::cout << reverseMe << std::endl;
	}

	{
		std::string reverseMe = "ABCDEFG";
		ReverseStringInPlace(reverseMe);
		std::cout << reverseMe << std::endl;
	}

	BST<int> bst;

	int arr[] = { 5, 2, 10, 7, 8, 1, 4 };
	bst.Insert(arr, sizeof(arr) / sizeof(arr[0]));
	std::cout << "In Order  :"; bst.PrintInOrder();
	std::cout << "In Order  :"; bst.PrintInOrderIterative();
	std::cout << "Pre Order :"; bst.PrintPreOrder();
	std::cout << "Pre Order :"; bst.PrintPreOrderIterative();
	std::cout << "Post Order:"; bst.PrintPostOrder();
	std::cout << "Post Order:"; bst.PrintPostOrderIterative();
	std::cout << "BFS:       "; bst.PrintBFS();

	{
		int iarr[] = { 1, 1, 2, 3, 4, 4, 5, 6, 7, 7, 7, 8, 9, 10 };

		qsort(iarr, sizeof(iarr) / sizeof(iarr[0]), sizeof(iarr[0]), int_cmp);

		PrintAllPairsWithSum(iarr, sizeof(iarr) / sizeof(iarr[0]), 8);
	}

	{
		std::vector<int> set1 = { 0, 1};
		std::vector<int> set2 = { 2 };

		std::vector<int> intersections = FindIntersectionsByHashSetLookup(set1, set2);
		std::copy(intersections.begin(), intersections.end(), std::ostream_iterator<int>(std::cout, ", "));
		std::cout << std::endl;

		std::vector<int> intersections2 = FindIntersectionsBySortedCompare(set1, set2);
		std::copy(intersections2.begin(), intersections2.end(), std::ostream_iterator<int>(std::cout, ", "));
		std::cout << std::endl;

		std::hash_set<int> stlIntersections;

		std::set_intersection(
			set1.begin(),
			set1.end(),
			set2.begin(),
			set2.end(),
			std::insert_iterator<std::hash_set<int>>(stlIntersections, stlIntersections.begin()));

		assert(std::is_permutation(intersections.begin(), intersections.end(), stlIntersections.begin()));
		assert(std::is_permutation(intersections2.begin(), intersections2.end(), stlIntersections.begin()));
		assert(std::is_permutation(intersections.begin(), intersections.end(), intersections2.begin()));
	}

	{
		std::vector<int> set1 = { 0, 1 };
		std::vector<int> set2 = { };

		std::vector<int> intersections = FindIntersectionsByHashSetLookup(set1, set2);
		std::copy(intersections.begin(), intersections.end(), std::ostream_iterator<int>(std::cout, ", "));
		std::cout << std::endl;

		std::vector<int> intersections2 = FindIntersectionsBySortedCompare(set1, set2);
		std::copy(intersections2.begin(), intersections2.end(), std::ostream_iterator<int>(std::cout, ", "));
		std::cout << std::endl;

		std::hash_set<int> stlIntersections;

		std::set_intersection(
			set1.begin(),
			set1.end(),
			set2.begin(),
			set2.end(),
			std::insert_iterator<std::hash_set<int>>(stlIntersections, stlIntersections.begin()));

		assert(std::is_permutation(intersections.begin(), intersections.end(), stlIntersections.begin()));
		assert(std::is_permutation(intersections2.begin(), intersections2.end(), stlIntersections.begin()));
		assert(std::is_permutation(intersections.begin(), intersections.end(), intersections2.begin()));
	}

	{
		std::vector<int> set1 = { 0, 1, 10, 9, 9, 3, 2};
		std::vector<int> set2 = { 2, 9,  9, 9, 6, 8, 0};

		std::vector<int> intersections = FindIntersectionsByHashSetLookup(set1, set2);
		std::copy(intersections.begin(), intersections.end(), std::ostream_iterator<int>(std::cout, ", "));
		std::cout << std::endl;

		std::vector<int> intersections2 = FindIntersectionsBySortedCompare(set1, set2);
		std::copy(intersections2.begin(), intersections2.end(), std::ostream_iterator<int>(std::cout, ", "));
		std::cout << std::endl;

		std::hash_set<int> stlIntersections;


		SortInPlace(set1);
		SortInPlace(set2);
		std::set_intersection(
			set1.begin(),
			set1.end(),
			set2.begin(),
			set2.end(),
			std::insert_iterator<std::hash_set<int>>(stlIntersections, stlIntersections.begin()));

		assert(std::is_permutation(intersections.begin(), intersections.end(), stlIntersections.begin()));
		assert(std::is_permutation(intersections2.begin(), intersections2.end(), stlIntersections.begin()));
		assert(std::is_permutation(intersections.begin(), intersections.end(), intersections2.begin()));
	}


	{
		int value = 0;
		LRUCache<int> m_cache(3);

		assert(false == m_cache.Lookup(1, value));

		m_cache.AddOrUpdate(1, 1);
		std::cout << m_cache << std::endl;

		assert(true == m_cache.Lookup(1, value));
		assert(value == 1);

		m_cache.AddOrUpdate(2, 2);
		std::cout << m_cache << std::endl;

		assert(true == m_cache.Lookup(2, value));
		assert(value == 2);

		m_cache.AddOrUpdate(3, 3);
		std::cout << m_cache << std::endl;

		assert(true == m_cache.Lookup(3, value));
		assert(value == 3);

		m_cache.AddOrUpdate(4, 4);
		std::cout << m_cache << std::endl;
		
		assert(false == m_cache.Lookup(1, value));

		m_cache.AddOrUpdate(1, 2);
		std::cout << m_cache << std::endl;

		assert(true == m_cache.Lookup(1, value));
		assert(value == 2);

		m_cache.AddOrUpdate(4, 5);
		std::cout << m_cache << std::endl;

		assert(true == m_cache.Lookup(4, value));
		assert(5 == value);
	}

	// Tree mirror testing
	{
		{
			//
			//     N
			//    / \
			//   A   A
			//  / \ / \
			//  B C C  B
			//  \      /
			//   D    D
			//Return : True

			TreeNode<string>* root = new TreeNode<string>("N");
			root->m_left = new TreeNode<string>("A");
			root->m_right = new TreeNode<string>("A");
			root->m_left->m_left = new TreeNode<string>("B");
			root->m_left->m_right = new TreeNode<string>("C");
			root->m_right->m_left = new TreeNode<string>("C");
			root->m_right->m_right = new TreeNode<string>("B");
			root->m_left->m_left->m_right = new TreeNode<string>("D");
			root->m_right->m_right->m_left = new TreeNode<string>("D");

			assert(IsTreeMirror(root));
		}

		{
			//		N
			//	   / \
			//    A   A
			//   / \ / \
			//  B  C C  D
			//    / \/ \
			//	  W R R W
			//	Return : False

			TreeNode<string>* root = new TreeNode<string>("N");
			root->m_left = new TreeNode<string>("A");
			root->m_right = new TreeNode<string>("A");
			root->m_left->m_left = new TreeNode<string>("B");
			root->m_left->m_right = new TreeNode<string>("C");
			root->m_right->m_left = new TreeNode<string>("C");
			root->m_right->m_right = new TreeNode<string>("D");  // Mismatch value
			root->m_left->m_right->m_left = new TreeNode<string>("W");
			root->m_left->m_right->m_right = new TreeNode<string>("R");
			root->m_right->m_left->m_left = new TreeNode<string>("R");
			root->m_right->m_right->m_left = new TreeNode<string>("W"); // Wrong order node

			assert(false == IsTreeMirror(root));
		}


	}

	{
		std::cout << "Press return to exit ..." << std::endl;
		string line;
		std::getline(std::cin, line);
	}
	return 0;
}
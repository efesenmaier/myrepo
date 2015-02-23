#include <cassert>
#include <stack>
#include <string>
#include <set>
#include <algorithm>
#include <hash_set>
#include <hash_map>

using namespace std;

int RomanCharToInt(char c)
{
	switch (c)
	{
	case 'M': return 1000;
	case 'D': return 500;
	case 'C': return 100;
	case 'L': return 50;
	case 'X': return 10;
	case 'V': return 5;
	case 'I': return 1;
	default: assert(false);
	}

	assert(false);
	return 0;
}

int RomanToInt(std::string roman)
{
	// 1. Do a scan to check if current numeral is negative.
	//
	std::stack<int> s;
	int sum = 0;

	for (std::string::iterator i = roman.begin(); i != roman.end(); ++i)
	{
		int value = RomanCharToInt(*i);
		bool hasNextValue = i + 1 < roman.end();
		int nextValue = 0;
		if (hasNextValue)
		{
			nextValue = RomanCharToInt(*(i + 1));

			// If the current value is less than the next, than it, and any deferred numerals, are negative.
			// (Ex. "IIV").
			//
			if (value < nextValue) 
			{
				sum -= value;
				while (s.size()) { sum -= s.top(); s.pop(); }
			}
			// If the current value is equal to the next, it is unclear whether the numeral is positive or negative.
			// Store it and defer evaluation. (Ex. "II...").
			//
			else if (value == nextValue)
			{
				s.push(value);
			}
			// If the current value is greater than the next, it clearly a positive value as well as any previously deferred.
			// Store it and defer evaluation. (Ex. "VVVI...").
			//
			else
			{
				sum += value;
				while (s.size()) { sum += s.top(); s.pop(); }
			}
		}
		// If the current value is the last value, it is clearly positive.
		// (Ex. "VVV").
		//
		else
		{
			sum += value;
			while (s.size()) { sum += s.top(); s.pop(); }
		}
	}

	return sum;
}

void FindRepeatingWords(
	/* [in]  */ const std::string& sentence,
	/* [out] */ std::hash_map<std::string, int>& dictionary,
	/* [out] */ std::vector<std::hash_map<std::string, int>::iterator>& repeatingWords)
{
	dictionary.clear();
	repeatingWords.clear();

	std::string delimiters = " ,!?.";

	// 1. Iterate across words in sentence.
	std::string::size_type lastPos = sentence.find_first_not_of(delimiters, 0);
	std::string::size_type pos = sentence.find_first_of(delimiters, lastPos);

	while (std::string::npos != pos || std::string::npos != lastPos)
	{
		// 2. Check if word exists in dictionary. If so, add to repeating words. Otherwise, add to dictionary.
		std::string word = sentence.substr(lastPos, pos - lastPos);
		std::transform(word.begin(), word.end(), word.begin(), ::tolower);

		// If word exists in dictionary, increase the word count.
		//
		std::hash_map<std::string, int>::iterator i = dictionary.find(word);
		if (i != dictionary.end())
		{
			++i->second;
			if (i->second == 2) repeatingWords.push_back(i);
		}
		// Otherwise, add it to the dictionary.
		//
		else
		{
			dictionary.insert(std::pair<std::string, int>(word, 1));
		}

		lastPos = sentence.find_first_not_of(delimiters, pos);
		pos = sentence.find_first_of(delimiters, lastPos);
	}
}

void XorSwap(char& x, char& y)
{
	x ^= y;
	y ^= x;
	x ^= y;
}

void ReverseStringInPlace(std::string& string)
{
	size_t size = string.size();
	for (size_t i = 0; i < size / 2; ++i)
	{
		XorSwap(string[i], string[size - 1 - i]);
	}
}

void CapitalizeWords(string& str)
{
	bool fWordChar = false;

	for (size_t i = 0; i < str.size(); ++i)
	{
		if (str[i] >= 'A' && str[i] <= 'z')
		{
			if (!fWordChar)
			{
				str[i] = toupper(str[i]);
				fWordChar = true;
			}
		}
		else
		{
			fWordChar = false;
		}
	}
}


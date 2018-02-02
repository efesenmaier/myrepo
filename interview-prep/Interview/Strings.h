using namespace std;

template <class T>
void Swap(T* a, T* b)
{
	T temp = *a;
	*a = *b;
	*b = temp;
}

template <class T>
void XorSwap(T* a, T* b)
{
	*x ^= *y;
	*y ^= *x;
	*x ^= *y;
}

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

void ReverseStringInPlace(std::string& string)
{
	if (string.size() < 2)
	{
		return;
	}

	char* i = &string[0];
	char* j = &string[string.size() - 1];
	while (i < j)
	{
		Swap(i, j);
		++i;
		--j;
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

void ReplaceSpacesWithPercent20(string& str)
{
	size_t numSpaces = 0;
	for (size_t i = 0; i < str.size(); ++i)
	{
		if (str[i] == ' ') ++numSpaces;
	}

	if (numSpaces == 0)
	{
		return;
	}

	char* i = &str[str.size() - 1];

	str.resize(str.size() + 2 * numSpaces, '\0');

	char* j = &str[str.size() - 1];

	while (i < j)
	{
		if (' ' == *i)
		{
			*j = '0';
			--j;
			*j = '2';
			--j;
			*j = '%';
		}
		else
		{
			*j = *i;
		}
		--j;
		--i;
	}
}

size_t CalculateNumDigits(size_t num, size_t base = 10)
{
	size_t numDigits = 0;
	do
	{
		++numDigits;
		num /= base;
	} while (num != 0);
	return numDigits;
}

bool CompressString(const string& str, string* newStr)
{
	size_t newLength = 0;
	char last = 0;
	size_t repeatCount = 0;
	for (size_t i = 0; i < str.size(); ++i)
	{
		if (last == str[i])
		{
			++repeatCount;
		}
		else
		{
			if (i != 0)
			{
				newLength += 1 + CalculateNumDigits(repeatCount);
			}
			repeatCount = 1;
			last = str[i];
		}
	}

	newLength += 1 + CalculateNumDigits(repeatCount);

	if (newLength > str.size())
	{
		return false;
	}

	newStr->resize(newLength, 0);

	last = 0;
	repeatCount = 0;
	int j = 0;
	for (size_t i = 0; i < str.size(); ++i)
	{
		if (last == str[i])
		{
			++repeatCount;
		}
		else
		{
			if (i != 0)
			{
				(*newStr)[j] = last;
				++j;
				sprintf_s(&(*newStr)[j], newStr->size() - j + 1, "%d", repeatCount);
				j += CalculateNumDigits(repeatCount);
			}
			repeatCount = 1;
			last = str[i];
		}
	}

	(*newStr)[j] = last;
	++j;
	sprintf_s(&(*newStr)[j], newStr->size() - j + 1, "%d", repeatCount);

	return true;
}




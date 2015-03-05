#include "Interview.h"

template <class T>
class Array
{
public:
	// Shallow copy constructor
	//
	Array(T* arr, size_t length) : m_arr(arr), m_length(length)
	{

	}

	const T& operator[] (size_t i) const
	{
		if (i >= m_length) throw exception("array out of bounds");

		return m_arr[i];
	}

	T& operator[] (size_t i)
	{
		if (i >= m_length) throw exception("array out of bounds");

		return m_arr[i];
	}

private:
	T* m_arr;
	size_t m_length;
};

template <class T>
class Matrix
{
public:
	Matrix(size_t height, size_t width, initializer_list<T> list) : m_height(height), m_width(width)
	{
		m_length = m_height * m_width;
		m_arr = new T[m_length];

		size_t j = 0;
		for (initializer_list<T>::iterator i = list.begin(); i < list.end(); ++i)
		{
			if (j >= m_length)
				break;

			m_arr[j] = *i;
			++j;
		}

		while (j < m_length)
		{
			m_arr[j] = T();
			++j;
		}
	}

	Array<T> operator[](size_t i)
	{
		if (i >= m_height) throw exception("index out of bounds");
		return Array<T>(&m_arr[i * m_width], m_width);
	}

	const Array<T> operator[](size_t i) const
	{
		if (i >= m_height) throw exception("index out of bounds");
		return Array<T>(&m_arr[i * m_width], m_width);
	}

	bool IsSquare() const { return (m_height == m_width); }

	size_t GetHeight() const { return m_height; }
	size_t GetWidth() const { return m_width; }
	size_t GetLength() const { return m_length; }
	T* GetData() { return m_arr; }

private:
	size_t m_height; // M - rows / height
	size_t m_width; // N - columns / width
	size_t m_length;
	T* m_arr;
};

template <class T>
void RotateRight(T* t1, T* t2, T* t3, T* t4)
{
	T temp = *t4;
	*t4 = *t3;
	*t3 = *t2;
	*t2 = *t1;
	*t1 = temp;
}

template <class T>
void RotateMatrixClockwise(Matrix<T>& m)
{
	if (!m.IsSquare()) throw exception("matrix is not square. it cannot be rotated.");

	for (size_t layer = 0; layer < m.GetHeight() / 2; ++layer)
	{
		for (size_t i = 0; i < m.GetWidth() - 1 - (2 * layer); ++i)
		{
			int r1 = layer;
			int c1 = i + layer;

			int r2 = layer + i;
			int c2 = m.GetWidth() - 1 - layer;

			int r3 = m.GetHeight() - 1 - layer;
			int c3 = m.GetWidth() - 1 - layer - i;

			int r4 = m.GetHeight() - 1 - layer - i;
			int c4 = layer;
			RotateRight(&m[r1][c1], &m[r2][c2], &m[r3][c3], &m[r4][c4]);
		}
	}
}

template <class T>
std::ostream& operator<<(std::ostream& os, const Matrix<T>& m)
{
	size_t maxWidth = 0;
	for (size_t r = 0; r < m.GetHeight(); ++r)
	{
		for (size_t c = 0; c < m.GetWidth(); ++c)
		{
			ostringstream oss;
			oss << m[r][c];
			size_t width = oss.str().size();
			if (width > maxWidth) maxWidth = width;
		}
	}

	for (size_t r = 0; r < m.GetHeight(); ++r)
	{
		for (size_t c = 0; c < m.GetWidth(); ++c)
		{
			os << setw(maxWidth) << m[r][c] << " ";
		}
		os << endl;
	}
	return os;
}
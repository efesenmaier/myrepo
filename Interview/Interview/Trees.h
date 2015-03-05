using namespace std;

template <class T>
struct TreeNode
{
	TreeNode(const T& val) : m_left(nullptr), m_right(nullptr), m_val(val) { }

	TreeNode<T>* m_left;
	TreeNode<T>* m_right;
	T m_val;
};

template <class T>
class BST
{
public:
	BST() : m_root(nullptr) { }


	void Insert(const T* vals, size_t count)
	{
		for (size_t i = 0; i < count; ++i)
		{
			Insert(vals[i]);
		}
	}

	void Insert(const T& val)
	{
		TreeNode<T>* newNode = new TreeNode<T>(val);

		if (nullptr == m_root)
		{
			m_root = newNode;
			return;
		}

		TreeNode<T>* curNode = m_root;

		while (true)
		{
			if (val <= curNode->m_val)
			{
				if (nullptr == curNode->m_left)
				{
					curNode->m_left = newNode;
					break;
				}
				else
				{
					curNode = curNode->m_left;
				}
			}
			else
			{
				if (nullptr == curNode->m_right)
				{
					curNode->m_right = newNode;
					break;
				}
				else
				{
					curNode = curNode->m_right;
				}
			}
		}
	}

	void PrintInOrder() const
	{
		PrintInOrderRecursive(m_root);
		std::cout << std::endl;
	}

	void PrintInOrderIterative()
	{
		std::stack<TreeNode<T>*> s;

		{
			TreeNode<T>* curNode = m_root;
			while (curNode)
			{
				s.push(curNode);
				curNode = curNode->m_left;
			}
		}
		
		while (!s.empty())
		{
			TreeNode<T>* curNode = s.top();
			s.pop();

			// Loop invariant: the node on the top has had its left subtree evaluated
			// Evaluate the node on top immediately
			//
			std::cout << curNode->m_val << " ";

			// Defer evaluation of the right sub-tree until after the left sub-tree
			//
			if (curNode->m_right)
			{
				s.push(curNode->m_right);
				curNode = curNode->m_right;

				if (curNode->m_left)
				{
					curNode = curNode->m_left;
					while (curNode)
					{
						s.push(curNode);
						curNode = curNode->m_left;
					}
				}
			}
		}

		std::cout << std::endl;
	}

	void PrintPreOrder() const
	{
		PrintPreOrderRecursive(m_root);
		std::cout << std::endl;
	}

	void PrintPreOrderIterative()
	{
		std::stack<TreeNode<T>*> s;

		s.push(m_root);

		while (!s.empty())
		{
			TreeNode<T>* curNode = s.top();
			s.pop();

			// Immediately evaluate the root
			//
			std::cout << curNode->m_val << " ";

			// Defer evaluation of the right sub-tree until after the left sub-tree
			//
			if (curNode->m_right)
			{
				s.push(curNode->m_right);
			}

			// Schedule evaluation of the left sub-tree
			//
			if (curNode->m_left)
			{
				s.push(curNode->m_left);
			}
		}

		std::cout << std::endl;
	}

	void PrintPostOrder() const
	{
		PrintPostOrderRecursive(m_root);
		std::cout << std::endl;
	}

	void PrintPostOrderIterative()
	{
		std::stack<TreeNode<T>*> s;

		if (nullptr == m_root)
		{
			std::cout << std::endl;
			return;
		}

		TreeNode<T>* curNode = m_root;

		do
		{
			// Move to leftmost node, pushing the root and right child as you go
			//
			while (curNode)
			{
				// Push root's right child and then root to stack.
				if (curNode->m_right)
					s.push(curNode->m_right);
				s.push(curNode);

				// Set root as root's left child  
				curNode = curNode->m_left;
			}

			// Pop an item from stack and set it as root
			//
			curNode = s.top();
			s.pop();

			// If the popped item has a right child and the right child is not
			// processed yet, then make sure right child is processed before root
			if (curNode->m_right && !s.empty() && s.top() == curNode->m_right)
			{
				s.pop();
				s.push(curNode);	// push root back to stack
				curNode = curNode->m_right;		// change root so that the right child is processed next
			}
			else  // Else print root's data and set root as NULL
			{
				std::cout << curNode->m_val << " ";
				curNode = nullptr;
			}
		} while (!s.empty());

		std::cout << std::endl;
	}

	void PrintBFS() const
	{
		std::queue<TreeNode<T>*> q;

		if (nullptr == m_root)
		{
			std::cout << std::endl;
			return;
		}

		TreeNode<T>* curNode = m_root;

		q.push(m_root);

		while (!q.empty())
		{
			TreeNode<T>* curNode = q.front();
			q.pop();
			std::cout << curNode->m_val << " ";

			if (curNode->m_left)
			{
				q.push(curNode->m_left);
			}

			if (curNode->m_right)
			{
				q.push(curNode->m_right);
			}
		}

		std::cout << std::endl;
	}

protected:
	void PrintInOrderRecursive(const TreeNode<T>* node) const
	{
		if (nullptr == node)
		{
			return;
		}

		PrintInOrderRecursive(node->m_left);
		std::cout << node->m_val << " ";
		PrintInOrderRecursive(node->m_right);
	}

	void PrintPreOrderRecursive(const TreeNode<T>* node) const
	{
		if (nullptr == node)
		{
			return;
		}

		std::cout << node->m_val << " ";
		PrintPreOrderRecursive(node->m_left);
		PrintPreOrderRecursive(node->m_right);
	}

	void PrintPostOrderRecursive(const TreeNode<T>* node) const
	{
		if (nullptr == node)
		{
			return;
		}

		PrintPostOrderRecursive(node->m_left);
		PrintPostOrderRecursive(node->m_right);
		std::cout << node->m_val << " ";
	}

	TreeNode<T>* m_root;
};

template <class T>
struct NodeInfo
{
	NodeInfo(TreeNode<T>* node, bool fLeft, int order) : m_node(node), m_fLeft(fLeft), m_order(order) { }
	TreeNode<T>* m_node;
	int m_order;
	bool m_fLeft;
};

template <class T>
bool IsTreeMirror(TreeNode<T>* root)
{
	if (nullptr == root)
	{
		return true;
	}

	queue<NodeInfo<T>> q;

	if (root->m_left)
	{
		q.push(NodeInfo<T>(root->m_left, true, 0));
	}
	if (root->m_right)
	{
		q.push(NodeInfo<T>(root->m_right, false, 0));
	}

	stack<NodeInfo<T>> stack;

	// While q is not empty
	while (!q.empty())
	{
		// Dequeue a node and process it.
		NodeInfo<T> n = q.front();
		q.pop();
		
		// If it is a left node, just push it on to a stack created for this row of the tree.
		if (n.m_fLeft)
		{
			if (n.m_order == 0 && !stack.empty())
			{
				return false;
			}

			stack.push(n);
		}

		// If it is a right node, check that a corresponding left node exists on the stack. If there is a mis-match return false
		if (!n.m_fLeft)
		{
			if (stack.empty())
			{
				std::cout << "No correpsonding node on left for node: " << n.m_node->m_val;
				return false;
			}
			NodeInfo<T> leftMirrorNode = stack.top();
			stack.pop();

			if (n.m_node->m_val != leftMirrorNode.m_node->m_val)
			{
				std::cout << "Value mismatch:" << leftMirrorNode.m_node->m_val << " " << n.m_node->m_val << std::endl;
				return false;
			}

			if (n.m_order != leftMirrorNode.m_order)
			{
				std::cout << "Order mismatch: " << leftMirrorNode.m_order << " " << n.m_order << std::endl;
				return false;
			}
		}

		// In both cases, enqueue the nodes children with the proper left flag and order.
		if (n.m_node->m_left)
		{
			int order = n.m_fLeft ? 2 * n.m_order : 2 * n.m_order + 1;
			q.push(NodeInfo<T>(n.m_node->m_left, n.m_fLeft, order));
		}

		if (n.m_node->m_right)
		{
			int order = n.m_fLeft ? 2 * n.m_order + 1 : 2 * n.m_order;
			q.push(NodeInfo<T>(n.m_node->m_right, n.m_fLeft, order));
		}
	}

	if (!stack.empty())
	{
		std::cout << "No mirror node on right for node: " << stack.top().m_node->m_val << std::endl;
		return false;
	}

	return true;
}
//
//bool IsMirrorImage(Tree)
//
//bool isMirrorImage(Node* leftSubtree, Node* rightSubtree)
//{
//	// 1. Check that root values of left and right subtree are equal'
//	if (nullptr == leftSubtree && nullptr == rightSubtree)
//	{
//		return true;
//	}
//	else if (nullptr == leftSubtree && nullptr != righSubtree)
//	{
//		return false;
//	}
//	else if (nullptr != leftSubtree && nullptr != rightSubtree)
//	{
//		return false;
//	}
//	else if (leftSubtree->value != rightSubtree->value)
//	{
//		return false;
//	}
//
//	if ((nullptr == leftSubtree->left) && rightSubtree->right && (leftSubtree->left->value != rightSubtree->right->value))
//	{
//		return false;
//	}
//
//	if (leftSubtree->right && rightSubtree->left && leftSubtree->right->value != rightSubtree->left->value)
//	{
//		return false;
//	}
//
//	// 2. Perform recursive mirror image check
//	return IsMirrorImage(leftSubtree->left, rightSubtree->right) && IsMirrorImage(leftSubtree->right, rightSubtree->left);
//}
'''
	Filename: Node.py
	Author: Chengyu Tang
	Date Created: 01/29/2019
	Python Version: 3.6.6
'''

class Edge():

	def __init__(self, weight = 0, type = None):
		# weight: int
		# type: str
		self.weight = weight
		self.type = type
		self.nodes = []

	def getWeight(self):
		return self.weight

	def setWeight(self, weight):
		self.weight = weight

	def getBothEnds(self):
		return self.nodes

	def getTheOtherEnd(self, node):
		# node: Node
		return self.nodes[0] if node == self.nodes[1] else self.nodes[1]

	def setNodes(self, node1, node2):
		# node1, node2: Node
		self.nodes = [node1, node2]
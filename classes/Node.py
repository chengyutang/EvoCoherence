'''
	Filename: Edge.py
	Author: Chengyu Tang
	Date Created: 01/29/2019
	Python Version: 3.6.6
'''

class Node():

	def __init__(self, name, activation):
		# name: str
		# activation: int
		self.name = name
		self.activation = activation
		self.neighbors = {} # {neight: edge}

	def getActivation(self):
		return self.activation

	def getName(self):
		return self.name

	def getNeighbors(self):
		return list(self.neighbors.keys())

	def addNeighbor(self, neighbor, edge):
		# neighbor: Node
		# edge: Edge
		self.neighbors[neighbor] = edge

	def update(self):
		pass
import random

class Node:
    def __init__(self, data):
        self.left = None
        self.right = None
        self.data = data

    def insert(self, data):
# Compare the new value with the parent node
        if self.data:
            if data < self.data:
                if self.left is None:
                    self.left = Node(data)
                else:
                    self.left.insert(data)
            elif data > self.data:
                if self.right is None:
                    self.right = Node(data)
                else:
                    self.right.insert(data)
        else:
            self.data = data

#binary search in a list
def binarySearch(target, data):
    low = 0
    high = len(data)-1
    while low <= high:
        i = int(((low+high) / 2))
        if data[i] == target:
            print(data[i])
            return True
        elif data[i] < target:
            low = i + 1
        elif data[i] > target:
            high = i - 1
    return False

#test binary in list
testlist = []

for i in range(101):
    testlist.append(i)
testlist.sort()
print(testlist)

print(binarySearch(45, testlist))

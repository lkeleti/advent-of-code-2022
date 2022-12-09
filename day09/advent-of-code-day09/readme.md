original source: [https://adventofcode.com/2022/day/9](https://adventofcode.com/2022/day/9)
## --- Day 9:  ---

head - tail

.....    .....		Head 3;1 Tail 1;0
...H. -> ..TH. -> 	diffX = 2; diffY = 1
.T...    .....		newTail 2;1

.....    .....		Head 1;1 Tail 3;0
.H.. ->  ..HT. ->  	diffX = -2; diffY = 1
...T.    .....		newTail 3;1

.T...    .....		Head 3;1 Tail 1;2
...H. -> ..TH. ->  	diffX = 2; diffY = -1
.....    .....		newTail 2;1

...T.    .....		Head 1;1 Tail 3;2
.H... -> ..HT. ->  	diffX = -2; diffY = -1
.....    .....		newTail 3;1
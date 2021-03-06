# Travelling Salesman Problem
Finding two optimal paths from dataset which contain equal amount of points. Below algorithms are taken into consideration:
* Brute Force (parameterized tries).
* Nearest Neighbor with random starting points.
* Local search on random generated paths. 
* Local search on nearest neighbor generated paths.

Those paths are visualizing using java swing with total paths length.

## Example
### Raw random data
<p align="center">
  <img width="750" src="../master/random_150.PNG">
</p>

### Nearest Neigbor with local search algorithm
<p align="center">
  <img width="750" src="../master/ls_nn_150.PNG">
</p>

### TODO

* Calculate delta function in NN algorithm as length of swapping edges instead of whole path.
* Local Search consider points or edges between two graphs.
* Add Iterated Local Search algorithm.
* Check metrics from 10 rerun.
* Cache points distance.

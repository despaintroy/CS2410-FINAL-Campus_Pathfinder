# USU Campus Pathfinder

USU Campus Pathfinder finds the fastest walking path between any buildings on USU's Logan campus. 

Every USU student has to think about how to get to their next class. This program is for the students who need to find the absolute shortest, most efficient path between any two buildings. 

__Features__
* Select two buildings and find the absolute shortest walking distance between them
* Display the path on a campus map
* Prioritize walking indoors rather than shortest path. Great for freezing cold and windy mornings.
* Pick any two arbitrary points to find the path between

## Installation

TODO: installation of JSON.simple

```bash
TODO: installation instructions
```

## Usage

TODO: Insert animated gifs showing features

## How It Works

The USU Pathfinder application works by applying Dijkstra's algorithm to an undirected graph. Edges on the graph contain their length and also whether they are indoors or outdoors. Nodes on the graph can be tagged if they are an entrance/exit of a specific building. When prioritizing walking indoors, Dijkstra's applies extra weight to edges that outdoors. 

## Support

> Troy DeSpain  
> [despaintroy@gmail.com](mailto:despaintroy@gmail.com)

## License
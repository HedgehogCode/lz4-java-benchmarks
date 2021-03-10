Building the benchmarks
```
$ mvn clean verify
```

Running the benchmarks
```
$ java -jar target/benchmarks.jar
```

Running short benchmarks
```
$ java -jar target/benchmarks.jar -f 1 -wi 5 -w 1s -i 5 -r 1s
```

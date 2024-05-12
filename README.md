# Bloom-Filter
Bloom Filter is a probabilistic data structure that can contain information about LARGE datasets. It is used when memory usage is a great concern. This filter is a bit-array and the hash functions. In the beginning, it is filled up with falses. To portray some dataset with such a filter it requires adding every element to this filter. The addition of an element to the filter is finding the hash values of the element and changing at corresponding positions in the filter array to trues. When all elements are added the filter is complete. To check if something is in the portrayed dataset one must just check if all proper positions in the filter array are true. There might be false positives, but there is a way to calculate the optimal filter size to the number of hash functions ratio to minimize the chance of occurrence of such mistakes.
I show two applications of such filter, the program "DistinctWords" uses Bloom Filter to find the number of distinct words in the text. The second proposal is used to compare big datasets.

## Instructions for "BloomFilter":
First to use the program, one must install the Scala compiler. To do so, enter the site and follow the given steps: https://docs.scala-lang.org/getting-started/index.html

Then to start the program one must type the following commands in a terminal:
```
scalac BloomFilter.scala
scala CountWordsBloomFilter <text filename> <text filename>
```
## Instructions for "DistinctWords":
First to use the program, one must install the Scala compiler. To do so, enter the site and follow the given steps: https://docs.scala-lang.org/getting-started/index.html

Then to start the program one must type the following commands in a terminal:
```
scalac DistinctWords.scala
scala Distinct <text filename> 
```

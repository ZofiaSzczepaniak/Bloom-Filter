import scala.io._
import scala.math._
import scala.util.hashing.MurmurHash3

class BloomFilter(val size: Int, val numHashes: Int) {
  //I defined class BloomFilter, which takes two parameters: size of the bloom filter and the number of hash functions.
  val bitArray: Array[Boolean] = Array.fill(size)(false) //I defined the table bloom filter, which contains falses.

  def add(word: String): Unit = {
    //I defined function add, which takes word and adds to filter.
    for (i <- 0 until numHashes) {
      val hash = hashString(word, i)
      bitArray(abs(hash % size)) = true
    }
  }

  def mightContain(word: String): Boolean = {
    //I defined the function mightContain, which takes word and check if the word is in the table.
    for (i <- 0 until numHashes) {
      val hash = hashString(word, i)
      if (!bitArray(abs(hash % size)))
        return false
    }
    true
  }
  def HammingWeight(): Int ={//Hamming weight of filter
    var c = 0
    for(i<- 0 until size)
      if(bitArray(i))
        c+=1
    c
  }
  private def hashString(word: String, seed: Int): Int = {
    //I defined the function which takes the word and hashes it with use of proper hash function.
    MurmurHash3.stringHash(word, seed)
  }
  def getNumHashes: Int = numHashes
  //I defined the method to get the number of hashes used.
  def getSize: Int = size
  //I defined the Method to get the size of the bit array.
}

object Distinct{
  def main(args: Array[String]): Unit = {
    val k = 6 //I defined the number of hash functions.
    val m = pow(2,17).toInt //I defined the size of the Bloom Filter.

    //
    val file1 = args(0)
    val fileSource1 = Source.fromFile(file1)
    val words1 = fileSource1.mkString.toLowerCase().split("\\W+")
   //I create the list of the words for file. I change words into a lowercase.
    fileSource1.close()

    //I create the Bloom Filters for text.
    val bloom = new BloomFilter(m,k)
    words1.foreach(bloom.add)
    //I calculate the Hamming weight of each filter.
    val X = bloom.HammingWeight()
    //I calculate the number of distinct words from the equation.
    val N = -m.toDouble/k.toDouble*log(1-X.toDouble/m.toDouble)
    //I print the result.
    println(s"Distinct words in the text: ${N.toInt}")
  }
}

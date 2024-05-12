import scala.io._
import scala.util.hashing.MurmurHash3
import scala.math._

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

object CountWordsBloomFilter {
  def main(args: Array[String]): Unit = {
    if (args.length < 2) { //I check if the user give a two files.
      println("Usage: scala CountWordsBloomFilter <file1> <file2>")
      System.exit(1)
    }

    val file = args(0)
    val filetwo = args(1)

    val fileSource = Source.fromFile(file)
    val fileSourcetwo = Source.fromFile(filetwo)

    val words = fileSource.mkString.toLowerCase().split("\\W+")
    val wordstwo = fileSourcetwo.mkString.toLowerCase().split("\\W+")
//And I create the two list of the words for both files. I change words into a lowercase.
    val bloomFilter = new BloomFilter(words.length * 10, 5)
//I call the class BloomFilter to create the Bloom Filter with size equal to size of the list of words times 10 and I set the number of hash function to 5.
    words.foreach(bloomFilter.add)
    //For every element from the list words, program adds it into a bloom filter.
    var falsePositives = 0
    //I defined the number of false positives.

    for (word <- wordstwo) { // I create the loop wich go through the words from the second book.
      if (bloomFilter.mightContain(word)) { // If word is in the BloomFilter.
        if (!words.contains(word)) // And the word is not in the first book.
          falsePositives += 1 //I add one to falsePositives.
      }
    }

    // Retrieve Bloom filter parameters and calculate theoretical false positive rate
    val numHashes = bloomFilter.getNumHashes // I defined the number of hash function.
    val size = bloomFilter.getSize //I create the size of the table.
    val theoreticalFalsePositiveRate = math.pow(1 - math.exp(-numHashes.toDouble * words.length / size), numHashes)
  //I calculate the false positives from theoretical prediction.
    println(s"False Positives: $falsePositives")
    println(s"Actual False Positive Rate: ${falsePositives.toDouble / wordstwo.length}")
    println(s"Theoretical False Positive Rate: $theoreticalFalsePositiveRate")
    //And I print the results.
    fileSource.close()
    fileSourcetwo.close()
  }
}
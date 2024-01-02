package org.example.cybercasino.utils.hashingAlgorithms;


//Interface implemented by all classes that do compute hashes with a particular algorithm
interface GenericHashAlgorithm {
    String getHash(String text);

    boolean checkHash(String text, String hash);
}

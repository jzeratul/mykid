package org.jzeratul.mykid.rest;

public interface EncryptorService {

  Long decLong(String encrypted);

  String encLong(Long id);

  String decStr(String encrypted);

  String encStr(String decrypted);

}

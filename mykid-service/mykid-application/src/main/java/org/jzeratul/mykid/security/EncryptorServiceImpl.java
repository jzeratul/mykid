package org.jzeratul.mykid.security;

import org.jasypt.encryption.StringEncryptor;
import org.jzeratul.mykid.rest.EncryptorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EncryptorServiceImpl implements EncryptorService {

  private static final Logger log = LoggerFactory.getLogger(MyKidSecurityServiceImpl.class);

  private final StringEncryptor jasyptEncryptorBean;

  public EncryptorServiceImpl(StringEncryptor jasyptEncryptorBean) {
    this.jasyptEncryptorBean = jasyptEncryptorBean;
  }

  @Override
  public Long decLong(String encrypted) {
    log.debug("Decrypting {} ", encrypted);
    if (encrypted == null) {
      log.debug("Null encrypted id, returning null");
      return null;
    }

    log.debug("Decrypt  " + encrypted);
    String decrypt = jasyptEncryptorBean.decrypt(encrypted);

    log.debug("Decrypted {} into {}", encrypted, decrypt);
    return Long.valueOf(decrypt);
  }

  @Override
  public String encLong(Long id) {
    return jasyptEncryptorBean.encrypt(id + "");
  }

  @Override
  public String decStr(String encrypted) {
    return jasyptEncryptorBean.decrypt(encrypted);
  }

  @Override
  public String encStr(String decrypted) {
    return jasyptEncryptorBean.encrypt(decrypted);
  }
}

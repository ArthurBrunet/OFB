# Projet OFB (ArthurBrunet)


## Exemple d'utilisation


Chiffrement: `java -jar OFB_Arthur.jar -e 7 10101010 .\test.txt`

Dechiffrement: `java -jar OFB_Arthur.jar -d 7 10101010 .\test.encrypted`

## Commandes

### 1er argument : 

 `-e` pour le chiffrement

 `-d` pour le déchiffrement

### 2eme argument : 

Nombre de bits de la clé (128, 192 ou 256)

*exemple :*`7`

### 3eme argument : 

La clé de chiffrement en binaire (plus grande que la longueur de la clé)

*exemple :*`10101010`

### 4eme argument : 

Le fichier à chiffrer ou déchiffrer

*exemple :*`./test.txt`

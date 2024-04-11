# Setup

## Add these to the environment variables:

```
"SPRING_DATASOURCE_PASSWORD" , "SPRING_DATASOURCE_URL" , "SPRING_DATASOURCE_USERNAME" , "JWT_KEY_ALIAS" , "JWT_KEYSTORE_PASSWORD" , "JWT_PREIVATE_KEY_PASSPHRASE" , "VERIFICATION_EMAIL" , "VERIFICATION_PASSWORD" 
```

## Starting database

```
docker run --name gag_postgres -e POSTGRES_DB=test -p 15432:5432 -e POSTGRES_USER=localuserdb -e POSTGRES_PASSWORD=localpassworddb postgres:12.14
```

## Setting Java Keytool

```
keytool -genkey -alias <alias> -keyalg RSA -keystore <keystore_name>  -keysize 2048
```

- alias — A name to uniquely identify the generated keypair entry within the generate keystore. Let’s use “jwtsigning”
- keyalg — Public key algorithm. Should be “RSA” for our case.
- keystore — A name for the keystore file generated. Let’s use “keystore.jks”
- keysize — Size (a measure of strength) of the generated public key. We should set that to 2048 at least. Can be set to 4096 for better security (to further reduce the possibility of an attacker guessing your keys).

If wanted to created new keystore.jks file, create using above command. Copy that to the following directory.

```src/main/resources/keys/keystore.jks```

## Curl
1. To register user
```curl
curl --location 'localhost:8080/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "fullName": "User Full Name",
    "email": "user5@gmail.com",
    "password": "abcd1234",
    "role": "USER"
}'
```
2. Login
```curl
curl --location --request POST 'localhost:8080/login?username=user%40gmail.com&password=abcd1234' \
--header 'Content-Type: application/x-www-form-urlencoded'
```

# verademo_scala

This sample scala project is built using the Scala Build Tool (sbt). It is meant to demonstrate and test the package firewall's capabilities to 

## Running
`sbt run`

## Nuances
SBT caches images of the packages it downloads to optimize performance, which is practically a good thing in most ways, but horrible for demoing. To show SBT is working, it helps to know that sbt caches examples of it's libraries after downloading.

This means that if you allow a package to be downloaded through the firewall, SBT will no longer try to download from the firewall, and will instead use the cached version - meaning if you allow a package on the firewall but then disallow it later, it doesnt matter! It's already downloaded and now SBT is using the cached version.

## Install SBT
[Install SBT with Coursier](https://www.scala-lang.org/download/)

# How to configure
This will walk you through configuring sbt to to use Phylum as a proxy server.
This walkthough is what is mentioned in the [sbt docs](https://www.scala-sbt.org/1.x/docs/Proxy-Repositories.html)

## Create a credentials file
Create a credentials file with the contents
```
realm=Veracode Package Firewall
host=maven.phylum.io
user=<PHYLUM_ORG>/<FIREWALL_NAME>
password=<PHYLUM_API_KEY>
```
I recommend placing this in the `$HOME/.ivy2/.credentials` location.

## Create repositories file.
```
[repositories]
  local
  my-maven-proxy-releases: https://maven.phylum.io/
```
This tells your sbt config to pull libraries from the phylum proxy, as well as looking for them locally.

## Set environment variable
You have to set your credentials file to the environment variable. If you used the file route suggested above, you can run this in your terminal.
```
export SBT_CREDENTIALS="$HOME/.ivy2/.credentials"
```

# DONE!
sbt should now be pointing to your firewall instance. Running `sbt run` should now return the following depending on your configuration:

```
[error] sbt.librarymanagement.ResolveException: Error downloading log4j:log4j:1.2.17
[error]   Not found
[error]   Not found
[error]   not found: /Users/stsessions/.ivy2/locallog4j/log4j/1.2.17/ivys/ivy.xml
[error]   download error: Caught java.io.IOException (Server returned HTTP response code: 424 for URL: https://maven.phylum.io/log4j/log4j/1.2.17/log4j-1.2.17.pom) while downloading https://maven.phylum.io/log4j/log4j/1.2.17/log4j-1.2.17.pom
```

To allow the package to be downloaded, allow the `log4j:log4j:1.2.17` package to your firewall. You will then be able to run the project successfully!

## Caching
`sbt` caches libraries, so once a library has been downloaded onto your device, sbt will use that library locally. This means if you allow the `log4j` package to be downloaded but then disable the firewall allowance, the program will still run.

To get around this, you have to clear the sbt cache. For Mac users, the Cache is located at:
```/Users/<username>/Library/Caches/Coursier/v1/https/maven.phylum.io/log4j/log4j/1.2.17/log4j-1.2.17.jar```

This is the cached vulnerable library in your local ecosystem for phylum. To clear the cache, run `rm -rf ~/Library/Caches/Coursier/v1/https/maven.phylum.io`.

If you're a Windows user, do the same thing but on windows :)

## Debugging
If something breaks, you can't find the cache to clear, etc. just spam AI until it gives you the fix to the problem.
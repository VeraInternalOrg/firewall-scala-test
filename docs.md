### Gradle

Custom registries can be configured in the `~/.gradle/init.gradle` file:

```groovy
allprojects {
    repositories {
        // Deactivate all registries which aren't protected.
        all { ArtifactRepository repo ->
            if (repo.url.toString() != "https://maven.phylum.io") {
                remove repo
            }
        }

        maven {
            url = "https://maven.phylum.io"
            credentials {
                username = "<PHYLUM_ORG>/<PHYLUM_GROUP>"
                password = "<PHYLUM_API_KEY>"
            }
        }
    }
}
```

A blocked package will show up in `gradle` output as failed:

```text
runtimeClasspath - Runtime classpath of source set 'main'.
+--- com.google.code.gson:gson:2.10.1 FAILED
+--- com.google.code.gson:gson:{strictly 2.10.1} -> 2.10.1 FAILED
\--- com.google.code.gson:gson:2.10.1 FAILED
```

If a version range is accepted by the manifest, the package manager will
automatically attempt to use a version that passes Phylum's policy.
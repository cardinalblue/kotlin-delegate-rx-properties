Release Process
===

The release version is defined in the `gradle.properties` file.

Automation with CI
---

Any commits to CI server will deploy a release and tag a release on Github.

Manually
---

Deploy to Bintray and JCenter by running the following command:

```
./gradlew clean build rx-delegate:bintrayUpload
```

Tag and submit the tag to GitHub, for example:

```
git tag vX.X.X
git push origin master --tag

```

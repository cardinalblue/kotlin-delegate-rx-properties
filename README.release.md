Release Process
===

The release version is defined in the `gradle.properties` file.

Automation with CI
---

### Steps
- Update the artifact version (`artifact_version`) in [gradle.properties](gradle.properties) and commit the change to any branches other than `master`.
- Create a PR from your working branch to `master`.
- Review the PR carefully.
- Once the PR is merged to `master`, the CI server will deploy a release to [JCenter](https://bintray.com/cblue/android/kotlin-delegate-rx-properties) and tag a release on Github.

Manually
---

### Steps
- Deploy to Bintray and JCenter by running the following command:

```
./gradlew clean build rx-delegate:bintrayUpload
```

- Tag and submit the tag to GitHub, for example:

```
git tag vX.X.X
git push origin master --tag

```

# How to install?

Add the following line to app-level build.gradle file, in dependencies scope:

```
plugins {
   // kapt or ksp
   id 'kotlin-kapt'
   id ("com.google.devtools.ksp") version "1.6.10-1.0.2"
}

//Make IDE aware of generated code if you use ksp
kotlin {
    sourceSets.debug {
        kotlin.srcDir("build/generated/ksp/debug/kotlin")
    }
}

dependencies {
    implementation "io.github.amrdeveloper:easyadapter:1.0.1"
    // kapt or ksp
    ksp "io.github.amrdeveloper:easyadapter-compiler:1.0.1"
}
```
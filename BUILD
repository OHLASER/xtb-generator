
java_binary(
    name = "XtbGeneratorRunner",
    srcs = glob(["src/main/java/**/*.java"]),
    resources = glob(["src/main/resources/**"]),
    deps = [
        "@closure_compiler//:compiler_lib",
        "@maven//:com_google_guava_guava",
        "@maven//:commons_cli_commons_cli",
    ],
    main_class = "sk.kuzmisin.xtbgenerator.XtbGeneratorRunner",
) 

java_test(
    name = "XtbGeneratorTest",
    srcs = glob(["src/test/java/**/*.java"]),
    resources = glob(["src/test/resources/**"]),
    deps = [
        "@closure_compiler//:compiler_lib",
        "@maven//:junit_junit",
        "XtbGeneratorRunner"
    ],
)
#        "@closure_compiler//:compiler_lib_no_runtime_libs",


# vi: se ts=4 sw=4 et:

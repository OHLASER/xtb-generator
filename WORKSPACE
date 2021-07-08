workspace(name = "xtb_generator")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-2.8",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/2.8.zip",
)


load("@rules_jvm_external//:defs.bzl", "maven_install")


http_archive(
    name = "rules_pkg",
    urls = [
        "https://mirror.bazel.build/github.com/bazelbuild/rules_pkg/releases/download/0.4.0/rules_pkg-0.4.0.tar.gz",
        "https://github.com/bazelbuild/rules_pkg/releases/download/0.4.0/rules_pkg-0.4.0.tar.gz",
    ],
    sha256 = "038f1caa773a7e35b3663865ffb003169c6a71dc995e39bf4815792f385d837d",
)

load("@rules_pkg//:deps.bzl", "rules_pkg_dependencies")
rules_pkg_dependencies()
maven_install(
    artifacts = [
        "com.google.guava:guava:30.1.1-jre",
        "com.google.javascript:closure-compiler:v20201207",
        "commons-cli:commons-cli:1.2",
        "junit:junit:4.11"
    ],
    repositories = [
        "https://repo1.maven.org/maven2"
    ],
)

load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")

git_repository(
    name = "google_bazel_common",
    commit = "9d1beb9294151cb1b28cd4b4dc842fd7559f9147",
    remote = "git://github.com/google/bazel-common.git",
)

git_repository(
    name = "protobuf_proto_rules",
    commit = "218ffa7dfa5408492dc86c01ee637614f8695c45",
    remote = "git://github.com/bazelbuild/rules_proto.git",
)

# Jarjar is a Google tool (https://github.com/google/jarjar) for generating
# shaded JARs (https://stackoverflow.com/questions/49810578). This repo contains
# Bazel bindings for Jarjar, under the Apache license.
git_repository(
    name = "com_github_johnynek_bazel_jar_jar",
    commit = "171f268569384c57c19474b04aebe574d85fde0d",
    remote = "git://github.com/johnynek/bazel_jar_jar.git",
)

git_repository(
    name = "protobuf_proto_rules",
    commit = "218ffa7dfa5408492dc86c01ee637614f8695c45",
    remote = "git://github.com/bazelbuild/rules_proto.git",
)

git_repository(
    name = "protobuf_java_rules",
    commit = "d7bf804c8731edd232cb061cb2a9fe003a85d8ee",
    remote = "git://github.com/bazelbuild/rules_java.git",
)

load("@protobuf_proto_rules//proto:repositories.bzl", "rules_proto_dependencies", "rules_proto_toolchains")

rules_proto_dependencies()
rules_proto_toolchains()


load("@com_github_johnynek_bazel_jar_jar//:jar_jar.bzl", "jar_jar_repositories")

jar_jar_repositories()

local_repository(
    name = "closure_compiler",
    path = "/Users/oh-laser/projects/xtb/closure-compiler"
)


load("@google_bazel_common//:workspace_defs.bzl", "google_common_workspace_rules", "maven_import")


google_common_workspace_rules()


maven_import(
    # https://truth.dev/protobufs
    group_id = "com.google.truth.extensions",
    artifact_id = "truth-liteproto-extension",
    version = "1.1",
    sha256 = "f637de4743194a870316a55a1c50c89638355a2323d96b6ced363a22d6ced316",
    licenses = ["notice"],
)


maven_import(
    # http://args4j.kohsuke.org/index.html
    group_id = "args4j",
    artifact_id = "args4j",
    version = "2.33",
    sha256 = "91ddeaba0b24adce72291c618c00bbdce1c884755f6c4dba9c5c46e871c69ed6",
    licenses = ["notice"],
)

maven_import(
    # https://github.com/google/gson
    group_id = "com.google.code.gson",
    artifact_id = "gson",
    version = "2.7",
    sha256 = "2d43eb5ea9e133d2ee2405cc14f5ee08951b8361302fdd93494a3a997b508d32",
    licenses = ["notice"],
)


maven_import(
    # https://github.com/google/re2j
    group_id ="com.google.re2j",
    artifact_id = "re2j",
    version ="1.3",
    sha256 = "d8040fa1c54c1ce208199015b6e599ec2ef37b7780f8f55a8b4b4b4299bade19",
    licenses = ["notice"],
)



# vi: se ts=4 sw=4 et:

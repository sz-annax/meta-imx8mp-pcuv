#
# Copyright (c) 2020-2024 Joel Winarske. All rights reserved.
#

SUMMARY = "ads"
DESCRIPTION = "A basic game with a banner ad."
AUTHOR = "Google"
HOMEPAGE = "None"
BUGTRACKER = "None"
SECTION = "graphics"

LICENSE = "CLOSED"

SRC_URI = "git://git@github.com/sz-annax/flutter-webrtc-app.git;protocol=ssh;branch=main"
SRCREV = "26d75d58b4b1614a537e50d075591db606117ff1"

S = "${UNPACKDIR}/git"

PUB_CACHE_EXTRA_ARCHIVE_PATH = "${WORKDIR}/pub_cache/bin"

PUBSPEC_APPNAME = "my_app_name"
FLUTTER_APPLICATION_INSTALL_SUFFIX = "flutter-webrtc-app"
PUBSPEC_IGNORE_LOCKFILE = "1"
# FLUTTER_APPLICATION_PATH = "samples/webrtc-app"

inherit flutter-app

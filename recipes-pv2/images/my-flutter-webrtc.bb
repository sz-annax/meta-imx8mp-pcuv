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

SRCREV = "478650cc0dc881c34a5a68d127380ed9d09d2d35"
SRC_URI = "git://git@github.com/sz-annax/flutter-webrtc-app.git;protocol=ssh;branch=main"

S = "${UNPACKDIR}/git"

PUB_CACHE_EXTRA_ARCHIVE_PATH = "${WORKDIR}/pub_cache/bin"
# PUB_CACHE_EXTRA_ARCHIVE_CMD = "flutter pub global activate melos; \
#     melos bootstrap"

PUBSPEC_APPNAME = "my_app_name"
FLUTTER_APPLICATION_INSTALL_SUFFIX = "flutter-webrtc-app"
PUBSPEC_IGNORE_LOCKFILE = "1"
# FLUTTER_APPLICATION_PATH = "samples/webrtc-app"

inherit flutter-app

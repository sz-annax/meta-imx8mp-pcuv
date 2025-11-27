SUMMARY = "GStreamer WebRTC sendrecv gtk4 application"
LICENSE = "CLOSED"

SRC_URI = "git://git@github.com/sz-annax/gst-webrtc-app.git;protocol=ssh;branch=main \
    "

SRCREV = "3f634f30b64aece4c8b5fe9c104f134954a9cd56"

S = "${WORKDIR}/git"

inherit meson pkgconfig

DEPENDS = " \
    gtk4 \
    libnice \
    glib-2.0 \
    json-glib \
    libsoup-2.4 \
    openssl \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-bad \
"

FILES:${PN} += "${bindir}/gst-webrtc-app"

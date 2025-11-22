SUMMARY = "GStreamer WebRTC sendrecv test app"
LICENSE = "CLOSED"

SRC_URI = " \
    file://webrtc-sendrecv.c \
    file://meson.build \
"

S = "${UNPACKDIR}"

inherit meson pkgconfig

DEPENDS = " \
    glib-2.0 \
    json-glib \
    libsoup-2.4 \
    openssl \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-bad \
"

# 安装可执行文件到 /usr/bin
FILES:${PN} += "${bindir}/webrtc-sendrecv"

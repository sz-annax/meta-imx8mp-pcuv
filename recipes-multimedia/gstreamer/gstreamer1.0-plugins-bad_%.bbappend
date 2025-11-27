PACKAGECONFIG:append = " openssl sctp dtls srtp webrtc"

DEPENDS:append = " libsrtp openssl libnice gnutls"

PACKAGECONFIG[srtp] = "-Dsrtp=enabled,-Dsrtp=disabled,libsrtp,libsrtp"
PACKAGECONFIG[dtls] = "-Ddtls=enabled,-Ddtls=disabled,gnutls"

PACKAGECONFIG_REMOVE = " \
    vulkan \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', 'gl', d)} \
"

PACKAGECONFIG:mx8mp-nxp-bsp:append = " openssl sctp dtls srtp webrtc"

DEPENDS:append = " libsrtp openssl libnice"

PACKAGECONFIG[srtp] = "-Dsrtp=enabled,-Dsrtp=disabled,libsrtp,libsrtp"

PACKAGECONFIG_REMOVE = " \
    vulkan \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', 'gl', d)} \
"

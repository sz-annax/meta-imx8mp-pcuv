project('webrtc-sendrecv', 'c', version: '1.0')

gst_deps = [
  dependency('gstreamer-1.0'),
  dependency('gstreamer-sdp-1.0'),
  dependency('gstreamer-webrtc-1.0'),
  dependency('json-glib-1.0'),
  dependency('libsoup-2.4'),
  dependency('glib-2.0')
]

executable(
  'webrtc-sendrecv',
  'webrtc-sendrecv.c',
  dependencies: gst_deps,
  install: true
)

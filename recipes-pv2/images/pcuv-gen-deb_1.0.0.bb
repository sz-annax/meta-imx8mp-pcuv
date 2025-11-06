SUMMARY = "PCUV deb upgrade package generator"
DESCRIPTION = "Generates PCUV upgrade tarball and package.json from existing deploy images"
LICENSE = "CLOSED"

SRC_URI = "file://gen_pcuv_app.py"

S = "${UNPACKDIR}"

inherit python3native

TARGET_NAME ?= "PCU-VIDEO"
TARGET_TYPE ?= "PCUV"
IMAGE_TYPE ?= "deb"

IMAGES_DIR ?= "${DEPLOY_DIR_IMAGE}"
OUT_DIR ?= "${DEPLOY_DIR_IMAGE}"

do_compile() {
    echo "==== PCUV deb package generation ===="
    echo "IMAGES_DIR = ${IMAGES_DIR}"
    echo "OUT_DIR    = ${OUT_DIR}"

    if [ ! -d "${IMAGES_DIR}" ]; then
        bbfatal "Image directory not found: ${IMAGES_DIR}"
    fi

    ${PYTHON} ${UNPACKDIR}/gen_pcuv_app.py \
        --output-dir "${OUT_DIR}" \
        --target-version "${PV}" \
        --target-name "${TARGET_NAME}" \
        --target-type "${TARGET_TYPE}" \
        --deploy-dir-deb "${DEPLOY_DIR_DEB}/${PACKAGE_ARCH}"

}

do_install[noexec] = "1"
do_compile[nostamp] = "1"

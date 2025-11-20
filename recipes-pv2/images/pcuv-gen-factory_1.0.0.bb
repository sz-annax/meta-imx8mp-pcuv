SUMMARY = "PCUV upgrade package generator"
DESCRIPTION = "Generates PCUV upgrade tarball and package.json from existing deploy images"
LICENSE = "CLOSED"

SRC_URI = "file://gen_pcuv.py \
           file://recoveryImage \
           file://recovery-rootfs.img"

S = "${UNPACKDIR}"

inherit python3native

TARGET_NAME ?= "PCU-VIDEO"
TARGET_TYPE ?= "PCUV"
IMAGE_TYPE ?= "factory"

IMAGES_DIR ?= "${DEPLOY_DIR_IMAGE}"
OUT_DIR ?= "${TMPDIR}/annax"

do_compile() {
    echo "==== PCUV factory package generation ===="
    echo "IMAGES_DIR = ${IMAGES_DIR}"
    echo "OUT_DIR    = ${OUT_DIR}"

    if [ ! -d "${IMAGES_DIR}" ]; then
        bbfatal "Image directory not found: ${IMAGES_DIR}"
    fi

    ${PYTHON} ${UNPACKDIR}/gen_pcuv.py \
        --output-dir "${OUT_DIR}" \
        --image-type "${IMAGE_TYPE}" \
        --target-version "${PV}" \
        --target-name "${TARGET_NAME}" \
        --target-type "${TARGET_TYPE}" \
        --uboot "${IMAGES_DIR}/imx-boot" \
        --kernel-image "${IMAGES_DIR}/Image" \
        --dtb "${IMAGES_DIR}/imx8mp-pcuv.dtb" \
        --rootfs "${IMAGES_DIR}/pcuv-image-imx8mp-pcu.rootfs.tar.zst" \
        --recovery-dtb "${IMAGES_DIR}/imx8mp-pcuv-recovery.dtb" \
        --recovery-image "${UNPACKDIR}/recoveryImage" \
        --recovery-rootfs "${UNPACKDIR}/recovery-rootfs.img"
}

do_install[noexec] = "1"
do_compile[nostamp] = "1"

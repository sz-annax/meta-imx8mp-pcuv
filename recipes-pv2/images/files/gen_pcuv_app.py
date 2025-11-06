#!/usr/bin/env python3
import argparse
import hashlib
import json
import tarfile
import shutil
import tempfile
from datetime import datetime, timezone
from pathlib import Path

# defaults are kept for standalone run
DEFAULT_TARGET_NAME = "PCU-VIDEO"
DEFAULT_TARGET_VERSION = "v0.0.1"
DEFAULT_TARGET_TYPE = "PCUV"
DEB_LIST = [
    {"name": "pcuv-app_0.0.1-r0_arm64.deb", "service": "pcuv-app.service"}
]

def sha256sum(filepath):
    h = hashlib.sha256()
    with open(filepath, "rb") as f:
        for chunk in iter(lambda: f.read(8192), b""):
            h.update(chunk)
    return h.hexdigest()

def main():
    p = argparse.ArgumentParser(description="Generate PCUV upgrade package")
    p.add_argument("--output-dir", "-o", default=None, help="Where to write package.json and final tar.gz")
    p.add_argument("--target-name", default=DEFAULT_TARGET_NAME)
    p.add_argument("--target-version", default=DEFAULT_TARGET_VERSION)
    p.add_argument("--target-type", default=DEFAULT_TARGET_TYPE)
    p.add_argument("--deploy-dir-deb", default=None, help="Path to deploy deb directory")
    args = p.parse_args()

    TARGET_NAME = args.target_name
    TARGET_VERSION = args.target_version
    TARGET_TYPE = args.target_type
    IMAGE_TYPE = "app"
    DEPLOY_DIR_DEB = ""

    if args.output_dir:
        OUT_DIR = Path(args.output_dir).resolve()
    else:
        raise ValueError("Output directory must be specified with --output-dir")

    if args.deploy_dir_deb:
        DEPLOY_DIR_DEB = Path(args.deploy_dir_deb).resolve()
    else:
        raise ValueError("Deploy deb directory must be specified with --deploy-dir-deb")

    JSON_FILE = OUT_DIR / "package.json"
    TARGET_FILE = OUT_DIR / f"{TARGET_NAME}-{IMAGE_TYPE}-{TARGET_VERSION}.tar.gz"

    print(f"Generating {TARGET_NAME}({TARGET_TYPE})-{IMAGE_TYPE} upgrade package ...")

    result = {
        "target": TARGET_NAME,
        "version": TARGET_VERSION,
        "date": datetime.now(timezone.utc).strftime("%Y%m%dT%H%M%S"),
        "devicetype": TARGET_TYPE,
        "debs": []
    }

    for deb in DEB_LIST:
        p = Path(f"{DEPLOY_DIR_DEB}/{deb['name']}")
        print("deb path:", p)
        if p.exists():
            deb['path'] = p.resolve()
            deb['sha'] = sha256sum(p)
            
            result['debs'].append({
                "name": deb['name'],
                "service": deb['service'],
                "sha": deb['sha']
            })
        else:
            raise FileNotFoundError(f"deb file not found: {p}")

    print(f"DEB_LIST: {DEB_LIST}")

    # JSON
    with open(JSON_FILE, "w") as f:
        json.dump(result, f, indent=4)

    with tarfile.open(TARGET_FILE, "w:gz") as tar:
        tar.add(JSON_FILE, arcname="package.json")
        for deb in DEB_LIST:
            print(f"deb: {deb}")
            tar.add(deb['path'], arcname=deb['name'])

    print(f"{TARGET_FILE} created successfully.")


if __name__ == "__main__":
    main()

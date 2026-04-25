import xml.etree.ElementTree as ET
import os
import sys


def find_xml_report(base_dir: str) -> str | None:
    for root_dir, _, files in os.walk(base_dir):
        for f in files:
            if f.endswith(".xml"):
                return os.path.join(root_dir, f)
    return None


def parse_counters(xml_path: str) -> dict[str, dict]:
    root = ET.parse(xml_path).getroot()
    return {c.get("type"): c for c in root.findall("counter")}


def write_output(counters: dict, output_file: str) -> None:
    with open(output_file, "a") as out:
        for metric in ["LINE", "BRANCH", "INSTRUCTION"]:
            if metric not in counters:
                continue
            c = counters[metric]
            covered = int(c.get("covered", 0))
            missed = int(c.get("missed", 0))
            total = covered + missed
            pct = round(covered / total * 100, 1) if total else 0.0
            key = metric.lower()
            out.write(f"{key}_covered={covered}\n")
            out.write(f"{key}_missed={missed}\n")
            out.write(f"{key}_total={total}\n")
            out.write(f"{key}_pct={pct}\n")


def main() -> None:
    base_dir = sys.argv[1] if len(sys.argv) > 1 else "app/build/reports/kover"
    github_output = os.environ.get("GITHUB_OUTPUT", "")

    xml_path = find_xml_report(base_dir)
    if not xml_path:
        print(f"No XML report found in {base_dir}", file=sys.stderr)
        if github_output:
            with open(github_output, "a") as f:
                f.write("available=false\n")
        sys.exit(0)

    print(f"Parsing: {xml_path}")
    counters = parse_counters(xml_path)

    if github_output:
        with open(github_output, "a") as f:
            f.write("available=true\n")
        write_output(counters, github_output)

    for metric in ["LINE", "BRANCH", "INSTRUCTION"]:
        if metric in counters:
            c = counters[metric]
            covered = int(c.get("covered", 0))
            missed = int(c.get("missed", 0))
            total = covered + missed
            pct = round(covered / total * 100, 1) if total else 0.0
            print(f"{metric}: {covered}/{total} ({pct}%)")


if __name__ == "__main__":
    main()

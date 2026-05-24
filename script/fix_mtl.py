from pathlib import Path

# Recursively find all .mtl files in the current directory
for mtl_file in Path("../").rglob("*.mtl"):
    lines_out = []

    with open(mtl_file, "r", encoding="utf-8") as f:
        for line in f:
            stripped = line.strip()

            # Check for lines starting with map_Kd and ending with .png
            if stripped.startswith("map_Kd") and stripped.endswith(".png"):
                parts = stripped.split(maxsplit=1)

                if len(parts) == 2:
                    texture = parts[1]

                    # Remove .png and prepend #
                    texture_no_ext = texture[:-4]
                    new_line = f"map_Kd #{texture_no_ext}\n"

                    lines_out.append(new_line)
                    continue

            lines_out.append(line)

    # Write modified contents back
    with open(mtl_file, "w", encoding="utf-8") as f:
        f.writelines(lines_out)

    print(f"Processed: {mtl_file}")

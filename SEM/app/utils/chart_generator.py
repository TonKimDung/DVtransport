def generate_chart(estimates):
    nodes = set()
    edges = []

    print("===== GENERATE CHART =====")
    print(estimates)
    print(type(estimates))

    for item in estimates:
        print("ITEM =", item)
        print("ITEM TYPE =", type(item))

        # Skip invalid item
        if not isinstance(item, dict):
            continue

        # SEM regression relation
        if item.get("op") == "~":
            source = item.get("rval")
            target = item.get("lval")
            estimate = item.get("Estimate")

            nodes.add(source)
            nodes.add(target)

            edges.append({
                "source": source,
                "target": target,
                "weight": round(float(estimate), 3)
                if estimate is not None else 0
            })

    return {
        "nodes": [{"id": n} for n in nodes],
        "edges": edges
    }
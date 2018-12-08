from math import sin, cos, sqrt, atan2, radians

def distance(lat1, lon1):
    print(lat1)
    print(lon1)
    lat1 = radians(lat1)
    lon1 = radians(lon1)
    cities = {"Sri City": [radians(13.5232), radians(79.9982)], "bareilly": [radians(28.3670), radians(79.4304)],
              "Ranchi": [radians(23.3441), radians(85.3096)], "New Delhi": [radians(28.6139), radians(77.2090)],
              "Udupi": [radians(13.3409), radians(74.7421)]}
    R = 6373.0
    distance_parity = 99999999
    for k in cities:
        print(k, cities[k])
        dlon = cities[k][1] - lon1
        dlat = cities[k][0] - lat1
        a = sin(dlat / 2) ** 2 + cos(lat1) * cos(cities[k][0]) * sin(dlon / 2) ** 2
        c = 2 * atan2(sqrt(a), sqrt(1 - a))
        distance = R * c

        print(distance)
        if distance < distance_parity:
            distance_parity = distance
            city = k
    return city


distance(13.5567,80.025)
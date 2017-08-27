CREATE OR REPLACE FUNCTION PI RETURN NUMBER AS 
BEGIN
  RETURN 3.14159365359;
END PI;

CREATE OR REPLACE FUNCTION GETDISTANCE 
(
  HOME_LAT IN NUMBER 
, HOME_LONG IN NUMBER 
, TARGET_LAT IN NUMBER
, TARGET_LONG IN NUMBER 
) RETURN NUMBER AS
BEGIN
  RETURN (ACOS
        (ROUND
            (SIN
                (HOME_LAT * PI() / 180) * SIN(TARGET_LAT * PI() / 180)
                + COS(HOME_LAT * PI() / 180)
                    * COS(TARGET_LAT * PI() / 180)
                    * COS((HOME_LONG - TARGET_LONG)
                    * PI() / 180), 35
            )
        ) * 180 / PI()
    ) * 60 * 1.8531;
END GETDISTANCE;
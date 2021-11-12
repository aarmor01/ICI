; -- FACTS --

; --- GAME INPUT DATA ---	
(deftemplate BLINKY
	(slot edible (type SYMBOL)) )

(deftemplate INKY
	(slot edible (type SYMBOL)) )

(deftemplate PINKY
	(slot edible (type SYMBOL)) )

(deftemplate SUE
	(slot edible (type SYMBOL)) )

(deftemplate MSPACMAN 
    (slot mindistancePPill (type NUMBER)) )

; --- ACTION ---
(deftemplate ACTION
	(slot id)
	(slot info (default ""))
	(slot priority (type NUMBER)) )   
	
; -- RULES --
(defrule PINKYchases
	(PINKY (edible false)) 
	=> 
	(assert (ACTION (id PINKYchases))) )

(defrule BLINKYrunsAway
	(PINKY (edible true)) 
	=>  
	(assert (ACTION (id PINKYrunsAway))) )
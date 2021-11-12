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
(defrule INKYchases
	(INKY (edible false)) 
	=> 
	(assert (ACTION (id INKYchases))) )

(defrule INKYrunsAway
	(INKY (edible true)) 
	=>  
	(assert (ACTION (id INKYrunsAway))) )
	
	
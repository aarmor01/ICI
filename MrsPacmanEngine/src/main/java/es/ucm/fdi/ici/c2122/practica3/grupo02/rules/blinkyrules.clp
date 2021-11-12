; -- FACTS --

; --- GAME INPUT DATA ---	
(deftemplate BLINKY
	(slot BLINKYedible (type SYMBOL))
    (slot BLINKYedTimeLeft (type NUMBER))
    (slot BLINKYoutOfLair (type SYMBOL))
	(slot BLINKYposition (type NUMBER))
	(slot distanceBLINKYToPacman (type NUMBER))
	(slot pacmanDistancePowerPill (type NUMBER))
	(slot nextPillPacManBySeer (type NUMBER))
	(slot anotherGhostInPath (type SYMBOL))
	(slot chaseCountBLINKY (type NUMBER)) )

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
(defrule BLINKYrunsAwayMSPACMANclosePPill
	(MSPACMAN (mindistancePPill ?d)) (test (<= ?d 30)) 
	=>  
	(assert (ACTION (id BLINKYrunsAway) 
			(info "MSPacMan cerca PPill"))) )

(defrule BLINKYrunsAway
	(BLINKY (edible true)) 
	=>  
	(assert (ACTION (id BLINKYrunsAway)
			(info "Comestible --> huir"))) )
	
(defrule BLINKYchases
	(BLINKY (edible false)) 
	=> 
	(assert (ACTION (id BLINKYchases)
			(info "No comestible --> perseguir"))) )	
	
	
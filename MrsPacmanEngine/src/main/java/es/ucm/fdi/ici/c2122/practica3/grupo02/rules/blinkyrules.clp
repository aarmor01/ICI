; -- FACTS --

; --- GAME INPUT DATA ---	
(deftemplate BLINKY
	(slot edible (type SYMBOL))
    (slot edTimeLeft (type NUMBER))
    (slot outOfLair (type SYMBOL))
	(slot position (type NUMBER))
	(slot distanceToPacman (type NUMBER))
	(slot anotherGhostInPath (type SYMBOL))
	(slot chaseCount (type NUMBER)) )

(deftemplate INKY
	(slot edible (type SYMBOL))
    (slot edTimeLeft (type NUMBER))
    (slot outOfLair (type SYMBOL))
	(slot position (type NUMBER))
	(slot distanceToPacman (type NUMBER))
	(slot anotherGhostInPath (type SYMBOL))
	(slot chaseCount (type NUMBER)) )

(deftemplate PINKY
	(slot edible (type SYMBOL))
    (slot edTimeLeft (type NUMBER))
    (slot outOfLair (type SYMBOL))
	(slot position (type NUMBER))
	(slot distanceToPacman (type NUMBER))
	(slot anotherGhostInPath (type SYMBOL))
	(slot chaseCount (type NUMBER)) )

(deftemplate SUE
	(slot edible (type SYMBOL))
    (slot edTimeLeft (type NUMBER))
    (slot outOfLair (type SYMBOL))
	(slot position (type NUMBER))
	(slot distanceToPacman (type NUMBER))
	(slot anotherGhostInPath (type SYMBOL))
	(slot chaseCount (type NUMBER)) )

(deftemplate MSPACMAN 
    (slot mindistancePPill (type NUMBER))
    (slot pacmanDistancePowerPill (type NUMBER))
	(slot nextPillPacManBySeer (type NUMBER)) )
	
(deftemplate CONSTANTS
	(slot ghostChaseDistance (type NUMBER))
	(slot minPredictionDistance (type NUMBER))
	(slot minIntersectionsBeforeChange (type NUMBER)) )

; --- ACTION ---
(deftemplate ACTION
	(slot id)
	(slot info (default ""))
	(slot priority (type NUMBER)) )   
	
; -- RULES --
;(defrule BLINKYrunsAwayMSPACMANclosePPill
;	(MSPACMAN (mindistancePPill ?d)) (test (<= ?d 30)) 
;	=>  
;	(assert (ACTION (id BLINKYRunsAway) 
;			(info "MSPacMan cerca PPill"))) )

(defrule BLINKYrunsAway
	(BLINKY (edible true))
	=>  
	(assert (ACTION (id BLINKYRunsAway)
			(info "Comestible --> huir")
			(priority 9))) )
	
(defrule BLINKYchases
	(BLINKY (edible false))
	(BLINKY (outOfLair true))
	(BLINKY (distanceToPacman ?d))
	(CONSTANTS (ghostChaseDistance ?g))
	(test (>= ?d ?g))
	=> 
	(assert (ACTION (id BLINKYChase)
			(info "No comestible --> perseguir")
			(priority 10))) )	
			
(defrule Seer
	(BLINKY (edible false))
	(BLINKY (outOfLair true))
	(BLINKY (distanceToPacman ?d))
	(CONSTANTS (minPredictionDistance ?g))
	(MSPACMAN (pacmanDistancePowerPill ?p))
	(test (> ?d ?g))
	(test (> ?p 20))
	=> 
	(assert (ACTION (id BLINKYSeer)
			(info "Seer predicts pill")
			(priority 10))) )
	
	
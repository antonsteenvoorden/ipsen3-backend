
--
-- Database: `testdatabase`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `wijn`
--

CREATE TABLE `wijn` (
  `wijn_id` int(11) NOT NULL,
  `wijn_serie_id` int(11) NOT NULL,
  `wijn_naam` varchar(100) NOT NULL,
  `wijn_inkoopprijs` double NOT NULL,
  `wijn_prijs` double NOT NULL,
  `wijn_type` int(11) NOT NULL,
  `wijn_jaartal` int(11) NOT NULL,
  `wijn_isactief` int(11) NOT NULL,
  `wijn_afkomst_naam` varchar(100) NOT NULL,
  `wijn_category_naam` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `wijn`
--

INSERT INTO `wijn` (`wijn_id`, `wijn_serie_id`, `wijn_naam`, `wijn_inkoopprijs`, `wijn_prijs`, `wijn_type`, `wijn_jaartal`, `wijn_isactief`, `wijn_afkomst_naam`, `wijn_category_naam`) VALUES
(1, 1, 'wijn_naam', 36, 37, 1, 1994, 1, 'Je moeder', 'Kaas');

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `wijn`
--
ALTER TABLE `wijn`
  ADD PRIMARY KEY (`wijn_id`);

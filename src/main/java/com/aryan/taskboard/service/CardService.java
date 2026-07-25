package com.aryan.taskboard.service;

import com.aryan.taskboard.model.BoardColumn;
import com.aryan.taskboard.model.Card;
import com.aryan.taskboard.repository.CardRepository;
import com.aryan.taskboard.repository.BoardColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BoardColumnRepository boardColumnRepository;

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public List<Card> getCardsByColumn(Long columnId) {
        return cardRepository.findByColumnId(columnId);
    }

    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }

    public Card moveCard(Long cardId, Long newColumnId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));
        BoardColumn newColumn = boardColumnRepository.findById(newColumnId)
                .orElseThrow(() -> new IllegalArgumentException("Column not found"));
        card.setColumn(newColumn);
        return cardRepository.save(card);
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}
// src/components/Question.jsx
import React from 'react';

const Question = ({ question, onAnswer }) => {
  return (
    <div className="absolute bottom-0 left-0 w-full p-4 bg-gray-800 text-white z-10">
      <div className="text-lg">{question.text}</div>
      <div className="mt-2 flex flex-wrap">
        {question.options.map((option, index) => (
          <button
            key={index}
            className="mr-2 px-4 py-2 bg-blue-500 rounded mt-2"
            onClick={() => onAnswer(option)}
          >
            {option}
          </button>
        ))}
      </div>
    </div>
  );
};

export default Question;

//src/components/TestLabel/TestLabel
import React from 'react';


type TestLabelProps = {
    text: string;
    clickable? : boolean;
  };

const TestLabel = ({ text, clickable }: TestLabelProps) => {
  return (
    <label className={`block text-sm font-medium ${clickable ? 'cursor-pointer' : ''}`}
    dangerouslySetInnerHTML={{ __html: text }}
    
    >
    </label>
  );
};

export default TestLabel;
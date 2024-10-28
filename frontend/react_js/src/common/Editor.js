import React, { useState, useEffect, useMemo } from 'react';
import ReactQuill from 'react-quill';
import DOMPurify from 'dompurify';
import 'react-quill/dist/quill.snow.css';
// npm install react-quill dompurify 터미널에 install 할것.

const QuillEditorComponent = ({ value, isTitle = false, isReadOnly = false, onChange }) => {
  const [content, setContent] = useState(value);

  useEffect(() => {
    setContent(value);
  }, [value]);

  const editorOptions = useMemo(() => {
    const baseOptions = isTitle
      ? {
          placeholder: '제목을 입력하세요',
          modules: {
            toolbar: [
              ['bold', 'italic', 'underline'],
              [{ size: ['small', false, 'large'] }],
              [{ color: [] }],
            ],
          },
          keyboard: {
            bindings: {
              enter: {
                key: 13,
                handler: () => false,
              },
            },
          },
        }
      : {
          placeholder: '내용을 입력하세요',
          modules: {
            toolbar: [
              ['bold', 'italic', 'underline', 'strike'],
              ['blockquote', 'code-block'],
              [{ header: 1 }, { header: 2 }],
              [{ list: 'ordered' }, { list: 'bullet' }],
              [{ script: 'sub' }, { script: 'super' }],
              [{ indent: '-1' }, { indent: '+1' }],
              [{ direction: 'rtl' }],
              [{ size: ['small', false, 'large', 'huge'] }],
              [{ header: [1, 2, 3, 4, 5, 6, false] }],
              [{ color: [] }, { background: [] }],
              [{ font: [] }],
              [{ align: [] }],
              ['clean'],
              ['link', 'image', 'video'],
            ],
          },
        };

    if (isReadOnly) {
      baseOptions.readOnly = true;
      baseOptions.modules = { toolbar: false };
    }

    return baseOptions;
  }, [isTitle, isReadOnly]);

  const handleChange = (html) => {
    setContent(html);
    onChange(html);
  };

  const sanitizeHTML = (html) => {
    return DOMPurify.sanitize(html);
  };

  return (
    <div className={`quill-editor ${isTitle ? 'title-editor' : ''} ${isReadOnly ? 'read-only' : ''}`}>
      {isReadOnly ? (
        <div dangerouslySetInnerHTML={{ __html: sanitizeHTML(content) }} />
      ) : (
        <ReactQuill
          value={content}
          onChange={handleChange}
          modules={editorOptions.modules}
          readOnly={editorOptions.readOnly}
          placeholder={editorOptions.placeholder}
        />
      )}
    </div>
  );
};

export default QuillEditorComponent;

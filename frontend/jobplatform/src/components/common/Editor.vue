<template>
    <div class="quill-editor" :class="{ 'title-editor': isTitle, 'read-only': isReadOnly }">
      <div v-if="isReadOnly" v-html="sanitizeHTML(content)"></div>
      <quill-editor
        v-else
        v-model:content="content"
        :options="editorOptions"
        @blur="onEditorBlur"
        @focus="onEditorFocus"
        @ready="onEditorReady"
        @change="onEditorChange"
        contentType="html"
      />
    </div>
  </template>
  
  <script>
  import { ref, watch, computed } from 'vue';
  import { quillEditor } from 'vue3-quill';
  import DOMPurify from 'dompurify';
  
  export default {
    name: 'QuillEditorComponent',
    components: { quillEditor },
    props: {
      modelValue: {
        type: String,
        default: ''
      },
      isTitle: {
        type: Boolean,
        default: false
      },
      isReadOnly: {
        type: Boolean
        
      }
    },
    emits: ['update:modelValue'],
    setup(props, { emit }) {
      const content = ref(props.modelValue);
  
      const editorOptions = computed(() => {
        const baseOptions = props.isTitle
          ? {
              placeholder: '제목을 입력하세요',
              modules: {
                toolbar: [
                  ['bold', 'italic', 'underline'],
                  [{ 'size': ['small', false, 'large'] }],
                  [{ 'color': [] }],
                ]
              },
              keyboard: {
                bindings: {
                  enter: {
                    key: 13,
                    handler: () => false
                  }
                }
              }
            }
          : {
              placeholder: '내용을 입력하세요',
              modules: {
                toolbar: [
                  ['bold', 'italic', 'underline', 'strike'],
                  ['blockquote', 'code-block'],
                  [{ 'header': 1 }, { 'header': 2 }],
                  [{ 'list': 'ordered'}, { 'list': 'bullet' }],
                  [{ 'script': 'sub'}, { 'script': 'super' }],
                  [{ 'indent': '-1'}, { 'indent': '+1' }],
                  [{ 'direction': 'rtl' }],
                  [{ 'size': ['small', false, 'large', 'huge'] }],
                  [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
                  [{ 'color': [] }, { 'background': [] }],
                  [{ 'font': [] }],
                  [{ 'align': [] }],
                  ['clean'],
                  ['link', 'image', 'video']
                ]
              }
            };
  
        if (props.isReadOnly) {
          baseOptions.readOnly = true;
          baseOptions.modules = { toolbar: false };
        }
  
        return baseOptions;
      });
  
      watch(() => props.modelValue, (newValue) => {
        if (newValue !== content.value) {
          content.value = newValue;
        }
      });
  
      watch(content, (newValue) => {
        emit('update:modelValue', newValue);
      });
  
      const onEditorChange = ({ html }) => {
        content.value = html;
      };
  
      const onEditorBlur = (quill) => {
        console.log('editor blur!', quill);
      };
  
      const onEditorFocus = (quill) => {
        console.log('editor focus!', quill);
      };
  
      const onEditorReady = (quill) => {
        console.log('editor ready!', quill);
      };
  
      const sanitizeHTML = (html) => {
        return DOMPurify.sanitize(html);
      };
  
      return {
        content,
        editorOptions,
        onEditorChange,
        onEditorBlur,
        onEditorFocus,
        onEditorReady,
        sanitizeHTML
      };
    }
  }
  </script>
  
  <style scoped>
  .quill-editor {
    height: 300px;
  }
  .quill-editor.title-editor {
    height: auto;
  }
  .quill-editor.title-editor .ql-editor {
    min-height: 40px;
    max-height: 60px;
  }
  .ql-editor {
    min-height: 200px;
  }
  .quill-editor.read-only {
    height: auto;
  }
  .quill-editor.read-only > div {
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 12px 15px;
    min-height: 200px;
  }
  </style>
  
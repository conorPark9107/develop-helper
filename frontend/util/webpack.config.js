const fs = require('fs');
const path = require('path');
const { minify } = require('terser');

// 입력 디렉토리와 출력 디렉토리를 배열로 정의
const directories = [
    { input: './static/js', output: './static/jsMin' },
    { input: './static/js/admin', output: './static/jsMin/admin' },
    { input: './static/js/market', output: './static/jsMin/market' },
    { input: './static/js/battle', output: './static/jsMin/battle' },
    { input: './static/js/board', output: './static/jsMin/board' },
    { input: './static/js/calculator', output: './static/jsMin/calculator' },
    { input: './static/js/killboard', output: './static/jsMin/killboard' },
    { input: './static/js/metabuild', output: './static/jsMin/metabuild' },
];

// 모든 디렉토리 순회
directories.forEach(({ input, output }) => {
    // 출력 디렉토리 생성
    if (!fs.existsSync(output)) {
        fs.mkdirSync(output, { recursive: true }); // 상위 디렉토리도 함께 생성
    }

    // 입력 디렉토리의 모든 파일을 순회
    fs.readdirSync(input).forEach(file => {
        const inputPath = path.join(input, file);
        const outputPath = path.join(output, file);

        if (path.extname(file) === '.js') {
            const code = fs.readFileSync(inputPath, 'utf8');
            minify(code).then(result => {
                fs.writeFileSync(outputPath, result.code, 'utf8');
                console.log(`난독화 완료: ${file} -> ${outputPath}`);
            }).catch(err => {
                console.error(`난독화 실패: ${file}`, err);
            });
        }
    });
});